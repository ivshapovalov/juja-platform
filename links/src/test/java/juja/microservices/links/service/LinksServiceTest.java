package juja.microservices.links.service;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LinksServiceTest {
    @Rule
    final public ExpectedException expectedException = ExpectedException.none();
    private LinksService service;
    @Mock
    private LinksRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new LinksServiceImpl(repository);
    }

    @Test
    public void testMockCreation() {
        assertNotNull(repository);
    }

    @Test
    public void saveLinkTest() {
        String url = "http://test.com";
        String id = "5a30508811d3b338a0b3f85c";
        Link expected = new Link(id, url);
        SaveLinkRequest request = new SaveLinkRequest(url);

        when(repository.saveLink(request)).thenReturn(expected);

        Link result = service.saveLink(request);

        assertEquals(expected, result);
        verify(repository).saveLink(request);
        verifyNoMoreInteractions(repository);
    }
}
