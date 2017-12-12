package juja.microservices.links.controller;

import juja.microservices.links.exceptions.ApiErrorMessage;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.service.LinksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/links")
public class LinksController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private LinksService linksService;
    private ApiErrorMessage errorMessage;

    @Autowired
    public LinksController(LinksService linksService, ApiErrorMessage errorMessage) {
        this.linksService = linksService;
        this.errorMessage = errorMessage;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveLink(@Valid @RequestBody SaveLinkRequest request) {
        logger.info("Received saveLink request: '{}'", request);
        Map<String, String> result = linksService.saveLink(request);

        if (result.get("id").isEmpty()) {
            errorMessage.setErrorCode(500);
            errorMessage.setExceptionMessage(String.format("Failed to process '%s'", request.toString()));
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllLinks() {
        return null;
    }
}
