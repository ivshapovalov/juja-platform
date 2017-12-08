package juja.microservices.links.controller;

import juja.microservices.links.model.SaveLinkRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/links")
public class LinksController {

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveLink(@RequestBody SaveLinkRequest request) {
        return null;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllLinks() {
        return null;
    }
}
