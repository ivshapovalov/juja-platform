package juja.microservices.slack.integration;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import juja.microservices.slack.archive.model.Channel;
import juja.microservices.slack.archive.repository.ArchiveRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class ArchiveRepositoryTest extends BaseIntegrationTest {

    @Inject
    private ArchiveRepository repository;

    @Test
    @UsingDataSet(locations = "/datasets/channels.json")
    public void getChannelsTest() {
        //when
        List<Channel> channels = repository.getChannels();

        //then
        assertNotNull(channels);
        assertEquals(2, channels.size());
    }

}
