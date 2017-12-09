package juja.microservices.links.repository;

import juja.microservices.links.model.Link;

import java.util.Set;

/**
 * @author Ivan Shapovalov
 */
public interface LinksRepository {

    Link saveLink();

    Set<Link> getAllLinks();

}
