package juja.microservices.links.service.impl;

import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.repository.LinksRepository;
import juja.microservices.links.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;


@Service
public class LinksServiceImpl implements LinksService {
    private LinksRepository linksRepository;

    @Autowired
    public LinksServiceImpl(LinksRepository linksRepository) {
        this.linksRepository = linksRepository;
    }

    @Override
    public Map<String, String> saveLink(SaveLinkRequest request) {
        return linksRepository.saveLink(request);
    }

    @Override
    public Set<Link> getAllLinks() {
        return null;
    }
}
