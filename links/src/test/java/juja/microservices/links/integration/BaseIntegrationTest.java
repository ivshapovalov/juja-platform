package juja.microservices.links.integration;

import com.github.fakemongo.Fongo;
import com.lordofthejars.nosqlunit.mongodb.MongoDbConfiguration;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.lordofthejars.nosqlunit.mongodb.SpringMongoDbRule;
import com.mongodb.MockMongoClient;
import com.mongodb.MongoClient;
import juja.microservices.links.repository.impl.LinksRepositoryImpl;
import org.junit.Rule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static juja.microservices.links.integration.LinksTestConfig.TEST_DATABASE_NAME;

@SpringBootTest(classes = {LinksTestConfig.class, LinksRepositoryImpl.class})
public class BaseIntegrationTest {

    @Rule
    public MongoDbRule mongoDbRule = getSpringFongoMongoDbRule();

    @Inject
    protected WebApplicationContext webApplicationContext;

    public static SpringMongoDbRule getSpringFongoMongoDbRule() {
        MongoDbConfiguration mongoDbConfiguration = new MongoDbConfiguration();
        mongoDbConfiguration.setDatabaseName(TEST_DATABASE_NAME);
        MongoClient mongo = MockMongoClient.create(new Fongo("this-fongo-instance-is-ignored"));
        mongoDbConfiguration.setMongo(mongo);
        return new SpringMongoDbRule(mongoDbConfiguration);
    }
}
