package juja.microservices.links.service.impl;

import juja.microservices.links.exception.InternalErrorException;
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
    public Link saveLink(SaveLinkRequest request) throws Exception {
        Link link = linksRepository.saveLink(request.getUrl());

        if (link == null) {
            throw new InternalErrorException(String.format("Failed to process '%s'", request.toString()));
        }

        return link;
    }

    @Override
    public List<Link> getAllLinks() {
        return linksRepository.getAllLinks();
    }
}
