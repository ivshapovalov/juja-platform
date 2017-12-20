package juja.microservices.links.controller;

import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.service.LinksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author Vladimir Zadorozhniy
 */

@RestController
@RequestMapping(value = "/v1/links")
public class LinksController {
    private LinksService linksService;

    public LinksController(LinksService linksService) {
        this.linksService = linksService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveLink(@RequestBody SaveLinkRequest request) {
        return null;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllLinks() {
        Set<Link> result = linksService.getAllLinks();
        return ResponseEntity.ok(result);
    }
}
