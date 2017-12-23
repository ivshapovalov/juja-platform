package juja.microservices.links.slackbot.exceptions;

import juja.microservices.links.slackbot.controller.LinksSlackbotController;
import juja.microservices.links.slackbot.service.LinksSlackbotService;
import juja.microservices.links.utils.TestUtils;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ivan Shapovalov
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LinksSlackbotController.class)
public class ExceptionHandlerTest {
    private String teamsSlackbotActivateTeamUrl = "/v1/commands/links/save";

    @Value("${message.save.link.instant}")
    private String messageSaveLinkInstant;
    @Inject
    private MockMvc mvc;
    @MockBean
    private LinksSlackbotService linksSlackbotService;
    @MockBean
    private RestTemplate restTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void handleWrongCommandFormatException() throws Exception {
        //given
        String saveLinkCommandText = "<http://url.com>";
        String responseUrl = "example.com";
        WrongCommandFormatException exception = new WrongCommandFormatException("wrong command");

        when(linksSlackbotService.saveLink(saveLinkCommandText)).thenThrow(exception);
        when(restTemplate.postForObject(eq(responseUrl), any(RichMessage.class), eq(String.class))).thenReturn("");

        //when
        mvc.perform(MockMvcRequestBuilders.post(TestUtils.getUrlTemplate(teamsSlackbotActivateTeamUrl),
                TestUtils.getUriVars("slashCommandToken", "/links-save", saveLinkCommandText,
                        "example.com"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string(messageSaveLinkInstant));

        //then
        verify(linksSlackbotService).saveLink(saveLinkCommandText);
        ArgumentCaptor<RichMessage> captor = ArgumentCaptor.forClass(RichMessage.class);
        verify(restTemplate).postForObject(eq(responseUrl), captor.capture(), eq(String.class));
        assertTrue(captor.getValue().getText().contains("wrong command"));
        verifyNoMoreInteractions(linksSlackbotService, restTemplate);
    }

    @Test
    public void handleResourceAccessException() throws Exception {
        //given
        String saveLinkCommandText = "<http://url.com>";
        String responseUrl = "example.com";
        ResourceAccessException exception = new ResourceAccessException("Some service unavailable");

        when(linksSlackbotService.saveLink(saveLinkCommandText)).thenThrow(exception);
        when(restTemplate.postForObject(eq(responseUrl), any(RichMessage.class), eq(String.class))).thenReturn("");

        //when
        mvc.perform(MockMvcRequestBuilders.post(TestUtils.getUrlTemplate(teamsSlackbotActivateTeamUrl),
                TestUtils.getUriVars("slashCommandToken", "/teams-activate", saveLinkCommandText,
                        "example.com"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string(messageSaveLinkInstant));

        //then
        verify(linksSlackbotService).saveLink(saveLinkCommandText);
        ArgumentCaptor<RichMessage> captor = ArgumentCaptor.forClass(RichMessage.class);
        verify(restTemplate).postForObject(eq(responseUrl), captor.capture(), eq(String.class));
        assertTrue(captor.getValue().getText().contains("Some service unavailable"));
        verifyNoMoreInteractions(linksSlackbotService, restTemplate);
    }

    @Test
    public void handleAllOtherExceptions() throws Exception {
        //given
        String saveLinkCommandText = "<http://url.com>";
        String responseUrl = "example.com";
        RuntimeException exception = new RuntimeException("other command");

        when(linksSlackbotService.saveLink(saveLinkCommandText)).thenThrow(exception);
        when(restTemplate.postForObject(eq(responseUrl), any(RichMessage.class), eq(String.class))).thenReturn("");

        //when
        mvc.perform(MockMvcRequestBuilders.post(TestUtils.getUrlTemplate(teamsSlackbotActivateTeamUrl),
                TestUtils.getUriVars("slashCommandToken", "/teams-activate", saveLinkCommandText,
                        "example.com"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string(messageSaveLinkInstant));

        //then
        verify(linksSlackbotService).saveLink(saveLinkCommandText);
        ArgumentCaptor<RichMessage> captor = ArgumentCaptor.forClass(RichMessage.class);
        verify(restTemplate).postForObject(eq(responseUrl), captor.capture(), eq(String.class));
        assertTrue(captor.getValue().getText().contains("other command"));
        verifyNoMoreInteractions(linksSlackbotService, restTemplate);
    }

    @Test
    public void handleNestedOtherAfterWrongCommandFormatException() throws Exception {
        //given
        String saveLinkCommandText = "<http://url.com>";
        String responseUrl = "example.com";
        WrongCommandFormatException wrongCommandFormatException = new WrongCommandFormatException("wrong command");
        Exception exception = new RuntimeException("Oops something went wrong :(");

        when(linksSlackbotService.saveLink(saveLinkCommandText)).thenThrow(wrongCommandFormatException);
        when(restTemplate.postForObject(eq(responseUrl), any(RichMessage.class), eq(String.class))).thenThrow(exception);

        //when
        mvc.perform(MockMvcRequestBuilders.post(TestUtils.getUrlTemplate(teamsSlackbotActivateTeamUrl),
                TestUtils.getUriVars("slashCommandToken", "/teams-activate", saveLinkCommandText,
                        responseUrl))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string(messageSaveLinkInstant));

        //then
        verify(linksSlackbotService).saveLink(saveLinkCommandText);
        ArgumentCaptor<RichMessage> captor = ArgumentCaptor.forClass(RichMessage.class);
        verify(restTemplate).postForObject(eq(responseUrl), captor.capture(), eq(String.class));
        assertTrue(captor.getValue().getText().contains("wrong command"));
        verifyNoMoreInteractions(linksSlackbotService, restTemplate);
    }
}