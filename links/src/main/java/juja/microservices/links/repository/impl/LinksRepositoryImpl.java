package juja.microservices.links.repository.impl;

import juja.microservices.links.model.Link;
import juja.microservices.links.repository.LinksRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ivan Shapovalov
 * @author Vladimid Zadorozhniy
 */

@Repository
public class LinksRepositoryImpl implements LinksRepository {

    private String mongoCollectionName;

    private MongoTemplate mongoTemplate;

    public LinksRepositoryImpl(MongoTemplate mongoTemplate, @Value("${spring.data.mongodb.collection}") String mongoCollectionName) {
        this.mongoTemplate = mongoTemplate;
        this.mongoCollectionName = mongoCollectionName;
    }

    @Override
    public Link saveLink() {
        return null;
    }

    @Override
    public List<Link> getAllLinks() {
        return mongoTemplate.findAll(Link.class, mongoCollectionName);
    }
}
