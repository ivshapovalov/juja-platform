package juja.microservices.slack.archive.service;

import juja.microservices.slack.archive.model.Channel;
import juja.microservices.slack.archive.repository.ArchiveRepository;
import juja.microservices.slack.archive.service.impl.ArchiveServiceImpl;
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
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArchiveServiceTest {

    private ArchiveService service;

    @Rule
    final public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private ArchiveRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new ArchiveServiceImpl(repository);
    }

    @Test
    public void testMockCreation() {
        assertNotNull(repository);
    }

    @Test
    public void saveMessageTest() {
        //TODO Should be implemented
    }

    @Test
    public void getChannelsTest() {
        List<Channel> expected = Arrays.asList(
                new Channel("CHANONEID", "flood"),
                new Channel("CHANTWOID", "feedback")
        );

        when(repository.getChannels()).thenReturn(expected);

        List<Channel> actual = service.getChannels();

        assertEquals(expected, actual);
        verify(repository).getChannels();
        verifyNoMoreInteractions(repository);
    }
}
