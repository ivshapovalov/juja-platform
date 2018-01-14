package juja.microservices.links.slackbot.service;

import juja.microservices.links.slackbot.exceptions.WrongCommandFormatException;
import juja.microservices.links.slackbot.model.links.HideLinkRequest;
import juja.microservices.links.slackbot.model.links.Link;
import juja.microservices.links.slackbot.model.links.SaveLinkRequest;
import juja.microservices.links.slackbot.model.users.User;
import juja.microservices.links.slackbot.repository.LinksRepository;
import juja.microservices.links.slackbot.repository.impl.LinksRepositoryImpl;
import juja.microservices.links.slackbot.service.impl.LinksSlackbotServiceImpl;
import juja.microservices.links.slackbot.service.impl.UserServiceImpl;
import juja.microservices.links.slackbot.util.SlackTextHandler;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.anyListOf;
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
    private UserService userService;

    @Before
    public void setUp() {
        linksRepository = mock(LinksRepositoryImpl.class);
        slackTextHandler = mock(SlackTextHandler.class);
        userService = mock(UserServiceImpl.class);
        linksSlackbotService = new LinksSlackbotServiceImpl(linksRepository, slackTextHandler, userService);
    }

    @Test
    public void saveLinkWhenTextContainsSeveralUrlsThrowsException() {
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
    public void saveLinkWhenTextNotContainUrlsThrowsException() {
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
    public void saveLinkWhenParsingMethodReturnsNullThrowsException() {
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
    public void saveLinkWhenTextContainsOneUrlExecutedCorrectly() {
        //given
        String text = "dfa <url1> asdfdasf";
        List<String> urls = Collections.singletonList("url1");
        Link expected = new Link("id1", "url1");
        when(slackTextHandler.getURLsFromText(text)).thenReturn(urls);
        ArgumentCaptor<SaveLinkRequest> captorSaveLinkRequest = ArgumentCaptor.forClass(SaveLinkRequest.class);
        when(linksRepository.saveLink(captorSaveLinkRequest.capture())).thenReturn(expected);

        //when
        Link actual = linksSlackbotService.saveLink(text);

        //then
        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(actual)
                    .as("expected not equals actual")
                    .isEqualTo(expected);
            soft.assertThat(captorSaveLinkRequest.getValue().getUrl())
                    .as("'captorSaveLinkRequest' url not equals expected url ")
                    .isEqualTo(expected.getUrl());
        });
        verify(linksRepository).saveLink(captorSaveLinkRequest.capture());
        verify(slackTextHandler).getURLsFromText(text);
        verifyNoMoreInteractions(slackTextHandler);
        verifyZeroInteractions(linksRepository);
    }

    @Test
    public void hideLinkExecutedCorrectly() {
        //given
        String text = "id1";
        String fromSlackUser = "slack-from";
        String owner = "uuid1";
        List<User> users = Collections.singletonList(new User(owner, fromSlackUser));
        Link expected = new Link(text, "url1");
        when(userService.findUsersBySlackUsers(anyListOf(String.class))).thenReturn(users);
        ArgumentCaptor<HideLinkRequest> captorHideLinkRequest = ArgumentCaptor.forClass(HideLinkRequest.class);
        when(linksRepository.hideLink(captorHideLinkRequest.capture())).thenReturn(expected);

        //when
        Link actual = linksSlackbotService.hideLink(fromSlackUser, text);

        //then
        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(actual)
                    .as("expected not equals actual")
                    .isEqualTo(expected);
            soft.assertThat(captorHideLinkRequest.getValue().getId())
                    .as("'captorHideLinkRequest' text not equals expected text")
                    .isEqualTo(text);
            soft.assertThat(captorHideLinkRequest.getValue().getOwner())
                    .as("'captorHideLinkRequest' owner not equals expected owner")
                    .isEqualTo(owner);
        });
        verify(userService).findUsersBySlackUsers(anyListOf(String.class));
        verify(linksRepository).hideLink(captorHideLinkRequest.capture());
        verifyZeroInteractions(slackTextHandler);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(linksRepository);
    }
}
