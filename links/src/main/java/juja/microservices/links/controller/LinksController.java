package juja.microservices.links.controller;

import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.service.LinksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Vladimir Zadorozhniy
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/links")
public class LinksController {
    private final LinksService linksService;

    public LinksController(LinksService linksService) {
        this.linksService = linksService;
    }

    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Link> saveLink(@Valid @RequestBody SaveLinkRequest request) throws Exception {
        log.info("Received save Link request: '{}'", request);
        Link link = linksService.saveLink(request);
        return ResponseEntity.ok(link);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Link>> getAllLinks() {
        log.info("Received get All Links request");
        List<Link> result = linksService.getAllLinks();
        return ResponseEntity.ok(result);
    }
}
