package juja.microservices.links.service.impl;

import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.repository.LinksRepository;
import juja.microservices.links.service.LinksService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ivan Shapovalov
 * @author Vladimir Zadorozhniy
 */

@Service
public class LinksServiceImpl implements LinksService {

    private LinksRepository linksRepository;

    public LinksServiceImpl(LinksRepository linksRepository) {
        this.linksRepository = linksRepository;
    }

    @Override
    public Link saveLink(SaveLinkRequest request) {
        return null;
    }

    @Override
    public List<Link> getAllLinks() {
        return linksRepository.getAllLinks();
    }
}
