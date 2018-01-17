package juja.microservices.links.slackbot.repository;

import juja.microservices.links.slackbot.model.links.HideLinkRequest;
import juja.microservices.links.slackbot.model.links.Link;
import juja.microservices.links.slackbot.model.links.SaveLinkRequest;

/**
 * @author Ivan Shapovalov
 */
public interface LinksRepository {

    Link saveLink(SaveLinkRequest saveLinkRequest);

    Link hideLink(HideLinkRequest hideLinkRequest);

}