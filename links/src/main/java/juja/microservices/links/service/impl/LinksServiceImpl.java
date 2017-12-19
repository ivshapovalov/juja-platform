package juja.microservices.links.service.impl;

import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.repository.LinksRepository;
import juja.microservices.links.service.LinksService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

/**
 * @author Ivan Shapovalov
 * @author Vladimir Zadorozhniy
 */

@Service
public class LinksServiceImpl implements LinksService {

    @Inject
    private LinksRepository linksRepository;

    public LinksServiceImpl(LinksRepository linksRepository) {
        this.linksRepository = linksRepository;
    }

    @Override
    public Link saveLink(SaveLinkRequest request) {
        return null;
    }

    @Override
    public Set<Link> getAllLinks() {
        return linksRepository.getAllLinks();
    }
}
