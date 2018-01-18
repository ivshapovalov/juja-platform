package juja.microservices.links.slackbot.service.impl;

import juja.microservices.links.slackbot.model.users.User;
import juja.microservices.links.slackbot.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ivan Shapovalov
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<User> findUsersBySlackUsers(List<String> slackUsers) {
        return null;
    }
}
