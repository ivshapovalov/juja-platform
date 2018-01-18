package juja.microservices.links.repository.impl;

import juja.microservices.links.exception.NotFoundException;
import juja.microservices.links.model.Link;
import juja.microservices.links.repository.LinksRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Ivan Shapovalov
 * @author Vladimid Zadorozhniy
 */
@Slf4j
@Repository
public class LinksRepositoryImpl implements LinksRepository {
    private MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.collection}")
    private String mongoCollectionName;

    public LinksRepositoryImpl(MongoTemplate mongoTemplate, @Value("${spring.data.mongodb.collection}") String mongoCollectionName) {
        this.mongoTemplate = mongoTemplate;
        this.mongoCollectionName = mongoCollectionName;
    }

    @Override
    public Link saveLink(String owner, String url) {
        Link link = new Link(owner, url);

        mongoTemplate.save(link, mongoCollectionName);
        log.info("Successfully saved link {}.", link);

        return link;
    }

    @Override
    public Link getLinkByURL(String owner, String url) throws NotFoundException {
        Link link = mongoTemplate.findOne(query(where("owner").is(owner).and("url").is(url)),
                Link.class, mongoCollectionName);
        if (link == null) {
            throw new NotFoundException(String.format("Not found link with url: [%s] which belongs to [%s].",
                    url, owner));
        }
        return link;
    }

    @Override
    public List<Link> getAllNotHiddenLinks() {
        Query query = new Query();
        query.addCriteria(Criteria.where("hidden").ne(true));
        List<Link> result = mongoTemplate.find(query, Link.class, mongoCollectionName);
        log.info("Found {} active links in database", result);
        return result;
    }
}
