package juja.microservices.links.slackbot.repository;

import juja.microservices.links.slackbot.model.Link;
import juja.microservices.links.slackbot.model.SaveLinkRequest;

/**
 * @author Ivan Shapovalov
 */
public interface LinksRepository {

    Link saveLink(SaveLinkRequest saveLinkRequest);

}