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
    public void saveNewLinkTest() throws Exception {
        String url = "http://test.com";
        String id = "5a30508811d3b338a0b3f85c";
        Link expected = new Link(id, url);
        SaveLinkRequest request = new SaveLinkRequest(url);

        when(linksRepository.getLinkByURL(url)).thenThrow(new NotFoundException(""));
        when(linksRepository.saveLink(url)).thenReturn(expected);

        Link actual = linksService.saveLink(request);

        assertEquals(expected, actual);
        verify(linksRepository).saveLink(url);
        verify(linksRepository).getLinkByURL(url);
        verifyNoMoreInteractions(linksRepository);
    }

    @Test
    public void saveExistingLinkTest() throws Exception {
        String url = "http://test.com";
        String id = "5a30508811d3b338a0b3f85c";
        Link expected = new Link(id, url);
        SaveLinkRequest request = new SaveLinkRequest(url);

        when(linksRepository.getLinkByURL(url)).thenReturn(expected);

        Link actual = linksService.saveLink(request);

        assertEquals(expected, actual);
        verify(linksRepository).getLinkByURL(url);
        verifyNoMoreInteractions(linksRepository);
    }

    @Test
    public void testGetAllLinks() {
        List<Link> expectedList = Arrays.asList(new Link("1", "www.test1.com"), new Link("2", "www.test2.net"));
        when(linksRepository.getAllLinks()).thenReturn(expectedList);
        List<Link> result = linksService.getAllLinks();
        assertEquals(result, expectedList);
    }
}
