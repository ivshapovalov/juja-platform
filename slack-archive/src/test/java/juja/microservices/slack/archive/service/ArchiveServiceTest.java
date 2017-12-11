package juja.microservices.slack.archive.service;

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

import static org.junit.Assert.assertNotNull;

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
}
