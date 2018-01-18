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
        String id = "5a30508811d3b338a0b3f85c";
        String owner = "a-user";
        Link expected = new Link(id, owner, url, false);
        SaveLinkRequest request = new SaveLinkRequest(owner, url);

        when(linksRepository.getLinkByURL(owner, url)).thenThrow(new NotFoundException(""));
        when(linksRepository.saveLink(owner, url)).thenReturn(expected);

        Link actual = linksService.saveLink(request);

        assertEquals(expected, actual);
        verify(linksRepository).saveLink(owner, url);
        verify(linksRepository).getLinkByURL(owner, url);
        verifyNoMoreInteractions(linksRepository);
    }

    @Test
    public void saveLinkWhenUrlExistsInDatabase() throws Exception {
        String url = "http://test.com";
        String id = "5a30508811d3b338a0b3f85c";
        String owner = "a-user";
        Link expected = new Link(id, owner, url, false);
        SaveLinkRequest request = new SaveLinkRequest(owner, url);

        when(linksRepository.getLinkByURL(owner, url)).thenReturn(expected);

        Link actual = linksService.saveLink(request);

        assertEquals(expected, actual);
        verify(linksRepository).getLinkByURL(owner, url);
        verifyNoMoreInteractions(linksRepository);
    }

    @Test
    public void getAllLinksExecutedCorrectly() {
        //given
        String owner = "owner";
        List<Link> expected = Arrays.asList(
                new Link(owner, "www.test1.com"),
                new Link(owner, "www.test2.net"));
        when(linksRepository.getAllNotHiddenLinks()).thenReturn(expected);

        //when
        List<Link> actual = linksService.getAllLinks();

        //then
        assertEquals(expected, actual);
        verify(linksRepository).getAllNotHiddenLinks();
        verifyNoMoreInteractions(linksRepository);
    }
}
