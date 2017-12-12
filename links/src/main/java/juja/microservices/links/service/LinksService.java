package juja.microservices.links.service;

import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;

import java.util.Map;
import java.util.Set;

/**
 * @author Ivan Shapovalov
 */
public interface LinksService {

    Map<String, String> saveLink(SaveLinkRequest request);

    Set<Link> getAllLinks();
}
