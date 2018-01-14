package juja.microservices.links.service;

import juja.microservices.links.exception.NotFoundException;
import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.repository.LinksRepository;
import juja.microservices.links.service.impl.LinksServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Ivan Shapovalov
 * @author Vladimir Zadorozhniy
 */

@RunWith(MockitoJUnitRunner.class)
public class LinksServiceTest {
    @Rule
    final public ExpectedException expectedException = ExpectedException.none();
    private LinksService linksService;
    @Mock
    private LinksRepository linksRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        linksService = new LinksServiceImpl(linksRepository);
    }

    @Test
    public void saveLinkWhenUrlNotExistsInDatabase() throws Exception {
        String url = "http://test.com";
        Link expected = new Link(url);
        SaveLinkRequest request = new SaveLinkRequest(url);

        when(linksRepository.getLinkByURL(url)).thenThrow(new NotFoundException(""));
        when(linksRepository.saveLink(url)).thenReturn(expected);

        Link actual = linksService.saveLink(request);

        assertEquals(expected, actual);
        verify(linksRepository).getLinkByURL(url);
        verify(linksRepository).saveLink(url);
        verifyNoMoreInteractions(linksRepository);
    }

    @Test
    public void saveLinkWhenUrlExistsInDatabase() throws Exception {
        String url = "http://test.com";
        Link expected = new Link(url);
        SaveLinkRequest request = new SaveLinkRequest(url);

        when(linksRepository.getLinkByURL(url)).thenReturn(expected);

        Link actual = linksService.saveLink(request);

        assertEquals(expected, actual);
        verify(linksRepository).getLinkByURL(url);
        verifyNoMoreInteractions(linksRepository);
    }

    @Test
    public void getAllLinksExecutedCorrectly() {
        //given
        List<Link> expected = Arrays.asList(
                new Link("www.test1.com"),
                new Link("www.test2.net"));
        when(linksRepository.getAllLinks()).thenReturn(expected);

        //when
        List<Link> actual = linksService.getAllLinks();

        //then
        assertEquals(expected, actual);
        verify(linksRepository).getAllLinks();
        verifyNoMoreInteractions(linksRepository);
    }
}
