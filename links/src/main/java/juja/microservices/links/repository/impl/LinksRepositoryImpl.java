package juja.microservices.links.repository.impl;

import juja.microservices.links.model.Link;
import juja.microservices.links.repository.LinksRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ivan Shapovalov
 * @author Vladimid Zadorozhniy
 */

@Repository
public class LinksRepositoryImpl implements LinksRepository {

    private String mongoCollectionName;

    @Inject
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
    public Set<Link> getAllLinks() {
        List<Link> links = mongoTemplate.findAll(Link.class, mongoCollectionName);
        return new LinkedHashSet<>(links);
    }
}
