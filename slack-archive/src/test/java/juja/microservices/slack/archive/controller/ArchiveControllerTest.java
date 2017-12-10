package juja.microservices.slack.archive.controller;

import juja.microservices.slack.archive.service.ArchiveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@WebMvcTest(ArchiveController.class)
public class ArchiveControllerTest {

    @Inject
    private MockMvc mockMvc;

    @MockBean
    private ArchiveService service;

    @Test
    public void saveMessageTest() {

    }

}
