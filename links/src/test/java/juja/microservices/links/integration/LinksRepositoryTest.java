package juja.microservices.links.integration;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import juja.microservices.links.model.Link;
import juja.microservices.links.repository.impl.LinksRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class LinksRepositoryTest extends BaseIntegrationTest {
    @Inject
    private LinksRepositoryImpl repository;

    @Test
    @UsingDataSet(locations = "/dataset/links-empty.json")
    public void saveNewLinkTest() {
        String url = "http://test.com";
        Link result = repository.saveLink(url);

        assertNotNull(result);
        assertThat(result.getId(), not(isEmptyString()));
        assertEquals(url, result.getUrl());
    }
}