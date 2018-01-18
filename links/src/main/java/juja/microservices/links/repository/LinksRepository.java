package juja.microservices.links.repository;

import juja.microservices.links.model.Link;

import java.util.List;

/**
 * @author Ivan Shapovalov
 */
public interface LinksRepository {
    Link saveLink(String owner, String url);

    List<Link> getAllNotHiddenLinks();

    Link getLinkByURL(String owner, String url);
}
