package juja.microservices.links.slackbot.repository.impl;

import feign.FeignException;
import juja.microservices.links.slackbot.exceptions.ApiError;
import juja.microservices.links.slackbot.exceptions.LinksExchangeException;
import juja.microservices.links.slackbot.model.links.HideLinkRequest;
import juja.microservices.links.slackbot.model.links.Link;
import juja.microservices.links.slackbot.model.links.SaveLinkRequest;
import juja.microservices.links.slackbot.repository.LinksRepository;
import juja.microservices.links.slackbot.repository.feign.LinksClient;
import juja.microservices.links.slackbot.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @author Ivan Shapovalov
 */
@Slf4j
@Repository
public class LinksRepositoryImpl implements LinksRepository {

    private final LinksClient linksClient;

    public LinksRepositoryImpl(LinksClient linksClient) {
        this.linksClient = linksClient;
    }

    @Override
    public Link saveLink(SaveLinkRequest saveLinkRequest) {
        Link link;
        try {
            link = linksClient.saveLink(saveLinkRequest);
        } catch (FeignException ex) {
            ApiError error = Utils.convertToApiError(ex.getMessage());
            throw new LinksExchangeException(error, ex);
        }
        log.info("Link is saved: '{}'", link.getId());
        return link;
    }

    @Override
    public Link hideLink(HideLinkRequest hideLinkRequest) {
        Link link;
        try {
            link = linksClient.hideLink(hideLinkRequest);
        } catch (FeignException ex) {
            ApiError error = Utils.convertToApiError(ex.getMessage());
            throw new LinksExchangeException(error, ex);
        }
        log.info("Link is hidden: '{}'", link.getId());
        return link;
    }
}
