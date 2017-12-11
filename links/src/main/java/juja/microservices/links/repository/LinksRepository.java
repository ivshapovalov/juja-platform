package juja.microservices.links.repository;

import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;

import java.util.Set;

/**
 * @author Ivan Shapovalov
 */
public interface LinksRepository {

    Link saveLink(SaveLinkRequest saveLinkRequest);

    Set<Link> getAllLinks();

}
