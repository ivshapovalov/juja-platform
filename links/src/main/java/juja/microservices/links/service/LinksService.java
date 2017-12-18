package juja.microservices.links.service;

import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;

import java.util.Set;

/**
 * @author Ivan Shapovalov
 */
public interface LinksService {

    Link saveLink(SaveLinkRequest request) throws Exception;

    Set<Link> getAllLinks();
}
