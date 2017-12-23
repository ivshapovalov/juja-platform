package juja.microservices.links.slackbot.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import juja.microservices.links.slackbot.exceptions.ExceptionsHandler;
import juja.microservices.links.slackbot.model.Link;
import juja.microservices.links.slackbot.service.LinksSlackbotService;
import lombok.extern.slf4j.Slf4j;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Ivan Shapovalov
 */
@RestController
@Slf4j
@RequestMapping(value = "/v1/commands")
public class LinksSlackbotController {

    private final RestTemplate restTemplate;
    private final LinksSlackbotService linksSlackbotService;
    private final ExceptionsHandler exceptionsHandler;

    @Value("${slack.slashCommandToken}")
    private String slackToken;
    @Value("${message.sorry}")
    private String messageSorry;
    @Value("${message.save.link.instant}")
    private String messageSaveLinkInstant;
    @Value("${message.save.link.delayed}")
    private String messageSaveLinkDelayed;

    public LinksSlackbotController(LinksSlackbotService linksSlackbotService,
                                   ExceptionsHandler exceptionsHandler,
                                   RestTemplate restTemplate) {
        this.linksSlackbotService = linksSlackbotService;
        this.exceptionsHandler = exceptionsHandler;
        this.restTemplate = restTemplate;
    }

    @ApiOperation(
            value = "Save new link in links storage",
            notes = "This method save new useful link in links storage"
    )
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully saved new link"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_METHOD, message = "Bad method"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNSUPPORTED_TYPE, message = "Unsupported request media type")
    })
    @PostMapping(value = "/links/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void onReceiveSlashCommandSaveLink(@RequestParam("token") String token,
                                              @RequestParam("text") String text,
                                              @RequestParam("response_url") String responseUrl,
                                              HttpServletResponse response) throws IOException {
        exceptionsHandler.setResponseUrl(responseUrl);
        if (isRequestCorrect(token, response, responseUrl)) {
            sendInstantResponseMessage(response, messageSaveLinkInstant);
            Link link = linksSlackbotService.saveLink(text);
            RichMessage message = new RichMessage(String.format(messageSaveLinkDelayed, link.getUrl()));
            sendDelayedResponseMessage(responseUrl, message);
            log.info("'Save link' command processed : text: '{}', response_url: '{}' and sent " +
                    "message to slack: '{}'", text, responseUrl, message.getText());
        }
    }

    private void sendInstantResponseMessage(HttpServletResponse response, String message) throws IOException {
        log.debug("Before sending instant response message '{}' ", message);
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(message);
        printWriter.flush();
        printWriter.close();
        log.info("After sending instant response message to slack '{}' ", message);
    }

    private void sendDelayedResponseMessage(String responseUrl, RichMessage message) {
        log.debug("Before sending delayed response message '{}' to slack response_url '{}' ",
                message.getText(), responseUrl);
        String response = restTemplate.postForObject(responseUrl, message, String.class);
        log.debug("After sending delayed response message. Response is '{}'", response);
    }

    private boolean isRequestCorrect(String token, HttpServletResponse response, String... params)
            throws IOException {
        log.debug("Before checking parameters of request from slack. Token '{}', other '{}' ", token,
                Arrays.stream(params).sorted().collect(Collectors.joining(",")));
        if (!token.equals(slackToken) ||
                Arrays.stream(params)
                        .anyMatch(param -> (param == null || param.isEmpty()))) {
            sendInstantResponseMessage(response, messageSorry);
            return false;
        }
        log.debug("After checking parameters of request from slack. Parameters is correct");
        return true;
    }
}

