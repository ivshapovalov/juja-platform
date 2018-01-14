package juja.microservices.links.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.service.LinksService;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ivan Shapovalov
 * @author Vladimir Zadorozhniy
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LinksController.class)
public class LinksControllerTest {
    private static final String LINKS_URL = "/v1/links";

    @Inject
    private MockMvc mockMvc;

    @MockBean
    private LinksService linksService;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void saveLink() throws Exception {
        //given
        String url = "http://test.com";
        Link link = new Link(url);
        SaveLinkRequest request = new SaveLinkRequest(url);
        String expected = asJsonString(link);

        when(linksService.saveLink(request)).thenReturn(link);

        //when
        String actual = mockMvc.perform(put(LINKS_URL)
                .content(asJsonString(request))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getAllLinks() throws Exception {
        //given
        List<Link> expected = Arrays.asList(
                new Link("www.test1.com"),
                new Link("www.test2.net"));
        when(linksService.getAllLinks()).thenReturn(expected);

        //when
        String actual = mockMvc.perform(get(LINKS_URL)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //then
        assertThatJson(actual).when(Option.IGNORING_ARRAY_ORDER).isEqualTo(expected);
        verify(linksService).getAllLinks();
        verifyNoMoreInteractions(linksService);
    }
}
