package juja.microservices.links.controller;

import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.service.LinksService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@AllArgsConstructor
@RequestMapping(value = "/v1/links")
public class LinksController {
    private final LinksService linksService;

    @PutMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> saveLink(@Valid @RequestBody SaveLinkRequest request) throws Exception {
        log.info("Received saveLink request: '{}'", request);
        return ResponseEntity.ok(linksService.saveLink(request));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllLinks() {
        List<Link> result = linksService.getAllLinks();
        return ResponseEntity.ok(result);
    }
}
