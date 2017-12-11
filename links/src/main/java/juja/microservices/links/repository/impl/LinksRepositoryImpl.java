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
    public Link saveLink(SaveLinkRequest request) {
        logger.debug("Going to save '{}'", request);
        mongoTemplate.save(request, mongoCollectionName);

        Link link = mongoTemplate.findOne(query(where("linkURL").is(request.getLinkURL())), Link.class, mongoCollectionName);
        if (link == null) {
            link = new Link();
            logger.error("Failed to add link to database. '{}'", request);
        } else {
            logger.info("Successfully added link to database. URL: {}, id: [{}]", link.getLinkURL(), link.getId());
        }

        return link;
    }

    @Override
    public Set<Link> getAllLinks() {
        return null;
    }
}
