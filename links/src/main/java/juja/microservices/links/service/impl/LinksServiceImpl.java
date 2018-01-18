package juja.microservices.links.service.impl;

import juja.microservices.links.exception.NotFoundException;
import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.repository.LinksRepository;
import juja.microservices.links.service.LinksService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ivan Shapovalov
 * @author Vladimir Zadorozhniy
 */
@Slf4j
@Service
@AllArgsConstructor
public class LinksServiceImpl implements LinksService {
    private LinksRepository linksRepository;

    @Override
    public Link saveLink(SaveLinkRequest request) {
        Link link;

        try {
            link = linksRepository.getLinkByURL(request.getOwner(), request.getUrl());
            log.info("Link already exists {}. ", link.toString());
        } catch (NotFoundException ex) {
            link = linksRepository.saveLink(request.getOwner(), request.getUrl());
        }

        return link;
    }

    @Override
    public List<Link> getAllLinks() {
        return linksRepository.getAllNotHiddenLinks();
    }
}
