package juja.microservices.slack.integration;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class ArchiveControllerIntegrationTest extends BaseIntegrationTest {

    private final String CHANNELS_URL = "/v1/slack-archive/channels";
    private MockMvc mockMvc;
    private final String FAKE_URL = "/fake-url";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @UsingDataSet(locations = "/datasets/channels.json")
    public void getAllChannelsAndReturnJson() throws Exception {
        String expected = "[{\"id\":\"CHANIDONE\",\"name\":\"flood\"},{\"id\":\"CHANIDTWO\",\"name\":\"feedback\"}]";

        String result = mockMvc.perform(get(CHANNELS_URL)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThatJson(result).isEqualTo(expected);
    }

    @Test
    public void getUsersBySlackNamesNotFound() throws Exception {
        //when
        mockMvc.perform(get(FAKE_URL)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }
}
