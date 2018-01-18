package juja.microservices.links.slackbot.service;

import juja.microservices.links.slackbot.model.users.User;

import java.util.List;

/**
 * @author Ivan Shapovalov
 */
public interface UserService {

    List<User> findUsersBySlackUsers(List<String> slackUsers);

}
