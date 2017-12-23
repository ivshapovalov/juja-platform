package juja.microservices.links.slackbot.service;

import juja.microservices.links.slackbot.model.Link;

/**
 * @author Ivan Shapovalov
 */
public interface LinksSlackbotService {

    Link saveLink(String text);

}