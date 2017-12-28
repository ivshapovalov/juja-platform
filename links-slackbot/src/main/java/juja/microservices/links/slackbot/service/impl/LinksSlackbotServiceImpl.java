package juja.microservices.links.slackbot.service.impl;

import juja.microservices.links.slackbot.exceptions.WrongCommandFormatException;
import juja.microservices.links.slackbot.model.Link;
import juja.microservices.links.slackbot.model.SaveLinkRequest;
import juja.microservices.links.slackbot.repository.LinksRepository;
import juja.microservices.links.slackbot.service.LinksSlackbotService;
import juja.microservices.links.slackbot.util.SlackTextHandler;
import juja.microservices.links.slackbot.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public LinksSlackbotServiceImpl(LinksRepository linksRepository, SlackTextHandler slackTextHandler) {
        this.linksRepository = linksRepository;
        this.slackTextHandler = slackTextHandler;
    }

    @Override
    public Link saveLink(String text) {
        List<String> urls = slackTextHandler.getURLsFromText(text);
        Utils.checkNull(urls, "Urls must not be null!");
        if (urls.size() != EXPECTED_URL_AMOUNT_IN_TEXT) {
            throw new WrongCommandFormatException("Text must contains one url");
        } else {
            String url = new ArrayList<>(urls).get(0);
            Link link = linksRepository.saveLink(new SaveLinkRequest(url));
            return link;
        }
    }
}
