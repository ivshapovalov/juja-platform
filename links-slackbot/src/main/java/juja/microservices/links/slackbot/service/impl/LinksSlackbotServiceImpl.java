package juja.microservices.links.slackbot.service.impl;

import juja.microservices.links.slackbot.exceptions.WrongCommandFormatException;
import juja.microservices.links.slackbot.model.links.HideLinkRequest;
import juja.microservices.links.slackbot.model.links.Link;
import juja.microservices.links.slackbot.model.links.SaveLinkRequest;
import juja.microservices.links.slackbot.model.users.User;
import juja.microservices.links.slackbot.repository.LinksRepository;
import juja.microservices.links.slackbot.service.LinksSlackbotService;
import juja.microservices.links.slackbot.service.UserService;
import juja.microservices.links.slackbot.util.SlackTextHandler;
import juja.microservices.links.slackbot.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ivan Shapovalov
 */
@Service
@Slf4j
public class LinksSlackbotServiceImpl implements LinksSlackbotService {

    private static final int EXPECTED_URL_AMOUNT_IN_TEXT = 1;

    private final LinksRepository linksRepository;
    private final SlackTextHandler slackTextHandler;
    private final UserService userService;

    public LinksSlackbotServiceImpl(LinksRepository linksRepository, SlackTextHandler slackTextHandler,
                                    UserService userService) {
        this.linksRepository = linksRepository;
        this.slackTextHandler = slackTextHandler;
        this.userService = userService;
    }

    @Override
    public Link saveLink(String text) {
        List<String> urls = slackTextHandler.getURLsFromText(text);
        Utils.checkNull(urls, "Urls must not be null!");
        if (urls.size() != EXPECTED_URL_AMOUNT_IN_TEXT) {
            throw new WrongCommandFormatException("Text must contains one url");
        } else {
            String url = new ArrayList<>(urls).get(0);
            return linksRepository.saveLink(new SaveLinkRequest(url));
        }
    }

    @Override
    public Link hideLink(String fromSlackUser, String text) {
        List<User> users = userService.findUsersBySlackUsers(Collections.singletonList(fromSlackUser));
        String owner = users.get(0).getUuid();
        return linksRepository.hideLink(new HideLinkRequest(owner, text));
    }
}
