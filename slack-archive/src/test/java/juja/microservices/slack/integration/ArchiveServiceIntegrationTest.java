package juja.microservices.slack.integration;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import juja.microservices.slack.archive.model.ChannelDTO;
import juja.microservices.slack.archive.repository.impl.ArchiveRepositoryImpl;
import juja.microservices.slack.archive.service.impl.ArchiveServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class ArchiveServiceIntegrationTest extends BaseIntegrationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    private ArchiveRepositoryImpl repository;

    @Inject
    private ArchiveServiceImpl service;

    @Test
    @UsingDataSet(locations = "/datasets/channels.json")
    public void getChannelsTest() {
        ChannelDTO channelOne = new ChannelDTO("CHANIDONE", "flood");
        ChannelDTO channelTwo = new ChannelDTO("CHANIDTWO", "feedback");

        List<ChannelDTO> actual = service.getChannels();

        assertNotNull(actual);
        assertTrue(actual.contains(channelOne));
        assertTrue(actual.contains(channelTwo));
    }
}
