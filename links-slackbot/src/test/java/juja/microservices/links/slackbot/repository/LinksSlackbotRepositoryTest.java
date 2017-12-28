package juja.microservices.links.slackbot.repository;

import feign.FeignException;
import juja.microservices.links.slackbot.exceptions.LinksExchangeException;
import juja.microservices.links.slackbot.model.Link;
import juja.microservices.links.slackbot.model.SaveLinkRequest;
import juja.microservices.links.slackbot.repository.feign.LinksClient;
import juja.microservices.links.slackbot.repository.impl.LinksRepositoryImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Ivan Shapovalov
 */
public class LinksSlackbotRepositoryTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();
    private LinksRepository linksRepository;
    private LinksClient linksClient;

    @Before
    public void setUp() {
        linksClient = mock(LinksClient.class);
        linksRepository = new LinksRepositoryImpl(linksClient);
    }

    @Test
    public void saveLinkSendRequestToRemoteLinksServerAndReturnLinkIdExecutedCorrectly() throws IOException {
        //given
        SaveLinkRequest saveLinkRequest = new SaveLinkRequest("url1");
        Link expected = new Link("id1", "url1");

        when(linksClient.saveLink(saveLinkRequest)).thenReturn(expected);

        //when
        Link actual = linksRepository.saveLink(saveLinkRequest);

        //then
        assertEquals(expected, actual);
        verify(linksClient).saveLink(saveLinkRequest);
        verifyNoMoreInteractions(linksClient);
    }

    @Test
    public void saveLinkSendRequestToRemoteLinksServerWhichReturnsErrorThrowsException() throws IOException {
        //given
        SaveLinkRequest saveLinkRequest = new SaveLinkRequest("url1");
        String expectedJsonResponseBody =
                "status 400 reading GatewayClient#saveLink(SaveLinkRequest); content:" +
                        "{\n" +
                        "  \"httpStatus\": 400,\n" +
                        "  \"internalErrorCode\": \"LKS-F1-D3\",\n" +
                        "  \"clientMessage\": \"Sorry, Links server return an error\",\n" +
                        "  \"developerMessage\": \"Exception - LinksExchangeException\",\n" +
                        "  \"exceptionMessage\": \"Something wrong on Links server\",\n" +
                        "  \"detailErrors\": []\n" +
                        "}";
        FeignException feignException = mock(FeignException.class);

        when(linksClient.saveLink(saveLinkRequest)).thenThrow(feignException);
        when(feignException.getMessage()).thenReturn(expectedJsonResponseBody);

        expectedException.expect(LinksExchangeException.class);
        expectedException.expectMessage(containsString("Sorry, Links server return an error"));

        try {
            //when
            linksRepository.saveLink(saveLinkRequest);
        } finally {
            //then
            verify(linksClient).saveLink(saveLinkRequest);
            verifyNoMoreInteractions(linksClient);
        }
    }
}
