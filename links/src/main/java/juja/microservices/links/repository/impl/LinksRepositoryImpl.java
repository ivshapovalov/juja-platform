package juja.microservices.links.repository.impl;

import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.repository.LinksRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.Set;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Slf4j
@Repository
public class LinksRepositoryImpl implements LinksRepository {
    @Inject
    private MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.collection}")
    private String mongoCollectionName;

    @Override
    public Link saveLink(SaveLinkRequest request) {
        Link link;
        String URL = request.getURL();
        String id = getLinkId(URL);

        if (id.isEmpty()) {
            mongoTemplate.save(request, mongoCollectionName);
            link = getLinkByURL(URL);
            if (link == null) {
                log.error("Failed to save link. '{}'", request);
            } else {
                log.info("Successfully saved link. URL: {}, id: [{}]", URL, link.getId());
            }
        } else {
            link = getLinkByURL(URL);
            log.info("Link already saved. URL: {}, id: [{}]", URL, id);
        }

        return link;
    }

    private Link getLinkByURL(String URL) {
        return mongoTemplate.findOne(query(where("URL").is(URL)), Link.class, mongoCollectionName);
    }

    private String getLinkId(String URL) {
        Link link = getLinkByURL(URL);
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
