package juja.microservices.slack.archive.controller;

import juja.microservices.slack.archive.model.ChannelDTO;
import juja.microservices.slack.archive.service.ArchiveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArchiveController.class)
public class ArchiveControllerTest {
    private final String CHANNELS_URL = "/v1/slack-archive/channels";

    @Inject
    private MockMvc mockMvc;

    @MockBean
    private ArchiveService service;

    @Test
    public void getChannelTest() throws Exception {
        List<ChannelDTO> channels = Arrays.asList(
                new ChannelDTO("CHANONEID", "flood"),
                new ChannelDTO("CHANTWOID", "feedback")
        );

        String expected = "[{\"id\":\"CHANONEID\",\"name\":\"flood\"},{\"id\":\"CHANTWOID\",\"name\":\"feedback\"}]";

        when(service.getChannels()).thenReturn(channels);

        String result = mockMvc.perform(get(CHANNELS_URL)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, result);
    }

    @Test()
    public void getHttpRequestMethodNotSupportedException() throws Exception {
        mockMvc.perform(post(CHANNELS_URL)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isMethodNotAllowed());
    }

}
