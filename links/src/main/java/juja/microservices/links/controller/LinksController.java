package juja.microservices.links.controller;

import juja.microservices.links.exceptions.ApiErrorMessage;
import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.service.LinksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/v1/links")
public class LinksController {
    private LinksService linksService;
    private ApiErrorMessage errorMessage;

    public LinksController(LinksService linksService, ApiErrorMessage errorMessage) {
        this.linksService = linksService;
        this.errorMessage = errorMessage;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveLink(@Valid @RequestBody SaveLinkRequest request) {
        log.info("Received saveLink request: '{}'", request);
        Link link = linksService.saveLink(request);

        if (link == null) {
            errorMessage.setErrorCode(500);
            errorMessage.setExceptionMessage(String.format("Failed to process '%s'", request.toString()));
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(link);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllLinks() {
        return null;
    }
}
