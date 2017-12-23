package juja.microservices.links.repository.impl;

import juja.microservices.links.model.Link;
import juja.microservices.links.repository.LinksRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
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
    public Link saveLink(String url) {
        Link link;
        HashMap<String, String> data = new HashMap<>();
        data.put("url", url);
        data.put("id", getLinkId(url));

        if (data.get("id").isEmpty()) {
            mongoTemplate.save(data, mongoCollectionName);
            link = getLinkByURL(url);
            log.info("Successfully saved link. url: {}, id: [{}]", link.getUrl(), link.getId());
        } else {
            link = getLinkByURL(url);
            log.info("Link already saved. url: {}, id: [{}]", url, data.get("id"));
        }

        return link;
    }

    public Link getLinkByURL(String url) {
        return mongoTemplate.findOne(query(where("url").is(url)), Link.class, mongoCollectionName);
    }

    private String getLinkId(String url) {
        Link link = getLinkByURL(url);
        if (link != null) {
            return link.getId();
        } else {
            return "";
        }
    }

    @Override
    public List<Link> getAllLinks() {
        return mongoTemplate.findAll(Link.class, mongoCollectionName);
    }
}
