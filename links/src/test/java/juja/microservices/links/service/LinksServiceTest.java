package juja.microservices.links.service;

import juja.microservices.links.model.Link;
import juja.microservices.links.repository.LinksRepository;
import juja.microservices.links.service.impl.LinksServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Ivan Shapovalov
 * @author Vladimir Zadorozhniy
 */
public class LinksServiceTest {

    private LinksService linksService;

    private LinksRepository linksRepository;

    @Before
    public void beforeEachTest() {
        linksRepository = mock(LinksRepository.class);
        linksService = new LinksServiceImpl(linksRepository);
    }

    @Test
    public void testGetAllLinks() throws Exception {
        Set<Link> expectedList = new HashSet(Arrays.asList(new Link("www.test1.com"), new Link("www.test2.net")));
        when(linksRepository.getAllLinks()).thenReturn(expectedList);
        Set<Link> result = linksService.getAllLinks();
        assertEquals(result, expectedList);
    }
}
