package juja.microservices.links.repository.impl;

import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.repository.LinksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class LinksRepositoryImpl implements LinksRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.collection}")
    private String mongoCollectionName;

    @Override
    public Map<String, String> saveLink(SaveLinkRequest request) {
        String URL = request.getURL();
        String id = getLinkId(URL);

        Map<String, String> result = new HashMap<>();
        result.put("id", id);

        if (id.isEmpty()) {
            mongoTemplate.save(request, mongoCollectionName);
            id = getLinkId(URL);
            result.put("id", id);
            if (id.isEmpty()) {
                logger.error("Failed to save link. '{}'", request);
            } else {
                logger.info("Successfully saved link. URL: {}, id: [{}]", URL, id);
            }
        } else {
            logger.info("Link already saved. URL: {}, id: [{}]", URL, id);
        }

        return result;
    }

    private String getLinkId(String URL) {
        Link link = mongoTemplate.findOne(query(where("URL").is(URL)), Link.class, mongoCollectionName);
        if (link != null) {
            return link.getId();
        } else {
            return "";
        }
    }

    @Override
    public Set<Link> getAllLinks() {
        return null;
    }
}
