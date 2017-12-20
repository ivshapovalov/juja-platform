package juja.microservices.links.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import juja.microservices.links.model.Link;
import juja.microservices.links.repository.impl.LinksRepositoryImpl;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.junit.Assert.assertEquals;


/**
 * @author Ivan Shapovalov
 * @author Vladimir Zadorozhniy
 */

public class LinksRepositoryTest {

    private static final String DB_NAME = "test-links";

    @ClassRule // Manage the mongod instance
    public static InMemoryMongoDb mongod = newInMemoryMongoDbRule().build();

    // Manage connection
    @Rule
    public MongoDbRule mongoRule = newMongoDbRule().defaultEmbeddedMongoDb(DB_NAME);

    private LinksRepository linksRepository;

    private MongoTemplate mongoTemplate;

    @Before
    public void beforeEachTest() {
        mongoTemplate = new MongoTemplate(mongoRule.getDatabaseOperation().connectionManager(), DB_NAME);
        linksRepository = new LinksRepositoryImpl(mongoTemplate, "links");
    }

    @Test
    @UsingDataSet(locations = {"/json/AllLinks.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void testGetAllLinks() throws IOException {
        List<Link> expected = getLinksFromJsonFile("src/test/resources/json/AllLinks.json");
        List<Link> result = linksRepository.getAllLinks();
        assertEquals(expected.size(), result.size());
        assertEquals(expected.toString(), result.toString());
    }

    private List<Link> getLinksFromJsonFile(String fileName) throws IOException {
        byte[] mapData = Files.readAllBytes(Paths.get(fileName));
        Map<String,List<Link>> myMap;
        ObjectMapper objectMapper = new ObjectMapper();
        myMap = objectMapper.readValue(mapData, HashMap.class);
        return myMap.get("links");
    }

}
