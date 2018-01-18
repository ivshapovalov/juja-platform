package juja.microservices.links.repository;

import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import juja.microservices.links.exception.NotFoundException;
import juja.microservices.links.model.Link;
import juja.microservices.links.repository.impl.LinksRepositoryImpl;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Ivan Shapovalov
 * @author Vladimir Zadorozhniy
 */
public class LinksRepositoryTest {

    private static final String DB_NAME = "test-links";
    @ClassRule
    public static InMemoryMongoDb mongod = newInMemoryMongoDbRule().build();
    @Rule
    final public ExpectedException expectedException = ExpectedException.none();
    @Rule
    public MongoDbRule mongoRule = newMongoDbRule().defaultEmbeddedMongoDb(DB_NAME);

    private LinksRepository linksRepository;

    private MongoTemplate mongoTemplate;

    @Before
    public void setUp() {
        mongoTemplate = new MongoTemplate(mongoRule.getDatabaseOperation().connectionManager(), DB_NAME);
        linksRepository = new LinksRepositoryImpl(mongoTemplate, "links");
        mongoTemplate.dropCollection("links");
    }

    @Test
    public void getAllLinksWhenOneLinkIsHiddenExecutedCorrectly() {
        //given
        String owner = "owner";
        List<Link> originalData = Arrays.asList(
                new Link("1", owner, "www.test1.com", false),
                new Link("2", owner, "www.test3.com", true),
                new Link("3", owner, "www.test2.net", false));
        mongoTemplate.insert(originalData, "links");

        List<Link> expected = Arrays.asList(
                new Link("1", owner, "www.test1.com", false),
                new Link("3", owner, "www.test2.net", false));

        //when
        List<Link> actual = linksRepository.getAllNotHiddenLinks();

        //then
        assertThat(actual, IsIterableContainingInAnyOrder.containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void getAllLinksWhenAllLinksIsHiddenReturnsEmptyList() {
        //given
        String owner = "owner";
        List<Link> originalData = Arrays.asList(
                new Link("1", owner, "www.test1.com", true),
                new Link("2", owner, "www.test3.com", true),
                new Link("3", owner, "www.test2.net", true));
        mongoTemplate.insert(originalData, "links");

        //when
        List<Link> actual = linksRepository.getAllNotHiddenLinks();

        //then
        assertTrue(actual.equals(new ArrayList<>()));
    }

    @Test
    public void getAllLinksWhenDbIsEmptyReturnsEmptyList() {
        //when
        List<Link> actual = linksRepository.getAllNotHiddenLinks();

        //then
        assertTrue(actual.equals(new ArrayList<>()));
    }

    @Test
    public void saveLinkExecutedCorrectly() {
        //given
        String url = "http://test.com";
        String owner = "owner";

        //when
        Link actual = linksRepository.saveLink(owner, url);

        //then
        assertNotNull(actual);
        assertThat(actual.getId(), not(isEmptyString()));
        assertEquals(url, actual.getUrl());
    }

    @Test
    public void getLinkByUrlWhenUrlNotExistsInDb() {
        //given
        String url = "www.test4.com";
        String owner = "owner";
        List<Link> originalData = Arrays.asList(
                new Link("1", owner, "www.test1.com", false),
                new Link("2", owner, "www.test3.com", false),
                new Link("3", owner, "www.test2.net", false));
        mongoTemplate.insert(originalData, "links");

        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage(
                String.format("Not found link with url: [%s] which belongs to [%s].", url, owner));

        //when
        linksRepository.getLinkByURL(owner, url);
    }

    @Test
    public void getLinkByUrlWhenUrlAlreadyExistsInDb() {
        //given
        String url = "www.test1.com";
        String owner = "www.test1.com";
        List<Link> originalData = Arrays.asList(
                new Link("1", owner, "www.test1.com", false),
                new Link("2", owner, "www.test3.com", false),
                new Link("3", owner, "www.test2.net", false));
        mongoTemplate.insert(originalData, "links");

        //when
        Link actual = linksRepository.getLinkByURL(owner, url);

        //then
        assertNotNull(actual);
        assertThat(actual.getId(), not(isEmptyString()));
        assertEquals(url, actual.getUrl());
    }
}
