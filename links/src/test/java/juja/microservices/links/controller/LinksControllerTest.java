package juja.microservices.links.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.service.LinksService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = {"juja.microservices.links.exception"})
@RunWith(SpringRunner.class)
@WebMvcTest(LinksController.class)
public class LinksControllerTest {
    private final String LINKS_URL = "/v1/links";

    @Inject
    private MockMvc mockMvc;

    @MockBean
    private LinksService linksService;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void saveLinkTest() throws Exception {
        String url = "http://test.com";
        String id = "5a30508811d3b338a0b3f85c";
        Link expected = new Link(id, url);
        SaveLinkRequest request = new SaveLinkRequest(url);
        String response = asJsonString(expected);

        when(linksService.saveLink(request)).thenReturn(expected);

        String result = mockMvc.perform(post(LINKS_URL)
                .content(asJsonString(request))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        assertEquals(response, result);
    }
}
