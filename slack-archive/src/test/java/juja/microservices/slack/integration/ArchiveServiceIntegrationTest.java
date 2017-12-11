package juja.microservices.slack.integration;

import juja.microservices.slack.archive.Application;
import juja.microservices.slack.archive.repository.impl.ArchiveRepositoryImpl;
import juja.microservices.slack.archive.service.impl.ArchiveServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class ArchiveServiceIntegrationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    private ArchiveRepositoryImpl repository;

    @Inject
    private ArchiveServiceImpl service;

    @Test
    public void saveMessageTest() {

    }
}
