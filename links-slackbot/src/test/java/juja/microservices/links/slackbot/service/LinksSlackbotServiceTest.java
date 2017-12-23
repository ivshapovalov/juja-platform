package juja.microservices.links.slackbot.service;

import juja.microservices.links.slackbot.exceptions.WrongCommandFormatException;
import juja.microservices.links.slackbot.model.Link;
import juja.microservices.links.slackbot.model.SaveLinkRequest;
import juja.microservices.links.slackbot.repository.LinksRepository;
import juja.microservices.links.slackbot.service.impl.LinksSlackbotServiceImpl;
import juja.microservices.links.slackbot.util.SlackTextHandler;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Ivan Shapovalov
 */
public class LinksSlackbotServiceTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private LinksRepository linksRepository;
    private SlackTextHandler slackTextHandler;
    private LinksSlackbotService linksSlackbotService;

    @Before
    public void setUp() {
        linksRepository = mock(LinksRepository.class);
        slackTextHandler = mock(SlackTextHandler.class);
        linksSlackbotService = new LinksSlackbotServiceImpl(linksRepository, slackTextHandler);
    }

    @Test
    public void saveTeamIfTextContainsSeveralUrlsThrowsException() {
        //given
        String text = "<url1> <url2>";
        List<String> urls = Arrays.asList("url1", "urls2");
        when(slackTextHandler.getURLsFromText(text)).thenReturn(urls);
        expectedException.expect(WrongCommandFormatException.class);
        expectedException.expectMessage("Text must contains one url");

        try {
            //when
            linksSlackbotService.saveLink(text);
        } finally {
            //then
            verify(slackTextHandler).getURLsFromText(text);
            verifyNoMoreInteractions(slackTextHandler);
            verifyZeroInteractions(linksRepository);
        }
    }

    @Test
    public void saveTeamIfTextNotContainUrlsThrowsException() {
        //given
        String text = "fasdfsafas";
        when(slackTextHandler.getURLsFromText(text)).thenReturn(new ArrayList<>());
        expectedException.expect(WrongCommandFormatException.class);
        expectedException.expectMessage("Text must contains one url");

        try {
            //when
            linksSlackbotService.saveLink(text);
        } finally {
            //then
            verify(slackTextHandler).getURLsFromText(text);
            verifyNoMoreInteractions(slackTextHandler);
            verifyZeroInteractions(linksRepository);
        }
    }

    @Test
    public void saveTeamIfParsingMethodReturnsNullThrowsException() {
        //given
        String text = "fasdfsafas";
        when(slackTextHandler.getURLsFromText(text)).thenReturn(null);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Urls must not be null!");

        try {
            //when
            linksSlackbotService.saveLink(text);
        } finally {
            //then
            verify(slackTextHandler).getURLsFromText(text);
            verifyNoMoreInteractions(slackTextHandler);
            verifyZeroInteractions(linksRepository);
        }
    }

    @Test
    public void saveTeamIfTextContainsOneUrlExecutedCorrectly() {
        //given
        String text = "dfa <url1> asdfdasf";
        List<String> urls = Collections.singletonList("url1");
        Link expected = new Link("id1", "url1");
        when(slackTextHandler.getURLsFromText(text)).thenReturn(urls);
        ArgumentCaptor<SaveLinkRequest> captor = ArgumentCaptor.forClass(SaveLinkRequest.class);
        when(linksRepository.saveLink(captor.capture())).thenReturn(expected);

        //when
        Link actual = linksSlackbotService.saveLink(text);

        //then
        assertEquals(expected, actual);
        verify(linksRepository).saveLink(captor.capture());
        assertTrue(captor.getValue().getUrl().equals(expected.getUrl()));
        verify(slackTextHandler).getURLsFromText(text);
        verifyNoMoreInteractions(slackTextHandler);
        verifyZeroInteractions(linksRepository);
    }
}
