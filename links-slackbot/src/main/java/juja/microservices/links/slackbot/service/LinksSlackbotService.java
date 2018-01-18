package juja.microservices.links.slackbot.service;

import juja.microservices.links.slackbot.model.links.Link;

/**
 * @author Ivan Shapovalov
 */
public interface LinksSlackbotService {

    Link saveLink(String text);

    Link hideLink(String fromSlackUser, String text);
}