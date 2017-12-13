package juja.microservices.links.service.impl;

import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.repository.LinksRepository;
import juja.microservices.links.service.LinksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class LinksServiceImpl implements LinksService {
    private LinksRepository linksRepository;

    public LinksServiceImpl(LinksRepository linksRepository) {
        this.linksRepository = linksRepository;
    }

    @Override
    public Link saveLink(SaveLinkRequest request) {
        return linksRepository.saveLink(request);
    }

    @Override
    public Set<Link> getAllLinks() {
        return null;
    }
}
