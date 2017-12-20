package juja.microservices.links.controller;

import juja.microservices.links.model.Link;
import juja.microservices.links.service.LinksService;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Shapovalov
 * @author Vladimir Zadorozhniy
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LinksController.class)
public class LinksControllerTest {

    @Inject
    private MockMvc mockMvc;

    @MockBean
    private LinksService linksService;

    private final String linksGetAllLinksUrl = "/v1/links";

    @Test
    public void testGetAllLinks() throws Exception {
        List<Link> expectedList = Arrays.asList(new Link("1", "www.test1.com"), new Link("2", "www.test2.net"));
        when(linksService.getAllLinks()).thenReturn(expectedList);

        String result = mockMvc.perform(get(linksGetAllLinksUrl)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThatJson(result).when(Option.IGNORING_ARRAY_ORDER).isEqualTo(expectedList);
        verify(linksService).getAllLinks();
        verifyNoMoreInteractions(linksService);
    }
}
