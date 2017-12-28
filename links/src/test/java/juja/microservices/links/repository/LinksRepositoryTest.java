package juja.microservices.links.repository;

import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import juja.microservices.links.model.Link;
import juja.microservices.links.repository.impl.LinksRepositoryImpl;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.List;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.junit.Assert.assertThat;


/**
 * @author Ivan Shapovalov
 * @author Vladimir Zadorozhniy
 */

public class LinksRepositoryTest {

    private static final String DB_NAME = "test-links";
    @ClassRule
    public static InMemoryMongoDb mongod = newInMemoryMongoDbRule().build();
    @Rule
    public MongoDbRule mongoRule = newMongoDbRule().defaultEmbeddedMongoDb(DB_NAME);
    private List<Link> expectedList =
            Arrays.asList(new Link("1", "www.test1.com"), new Link("2", "www.test2.net"));
    private LinksRepository linksRepository;

    @Before
    public void setUp() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoRule.getDatabaseOperation().connectionManager(), DB_NAME);
        linksRepository = new LinksRepositoryImpl(mongoTemplate, "links");
        mongoTemplate.insert(expectedList, "links");
    }

    @Test
    public void testGetAllLinks() {
        List<Link> result = linksRepository.getAllLinks();
        assertThat(result, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedList.toArray()));
    }
}
