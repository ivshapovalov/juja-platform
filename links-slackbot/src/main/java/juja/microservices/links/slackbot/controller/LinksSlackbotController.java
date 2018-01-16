package juja.microservices.links.slackbot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import juja.microservices.links.slackbot.exceptions.ExceptionsHandler;
import juja.microservices.links.slackbot.model.links.Link;
import juja.microservices.links.slackbot.service.LinksSlackbotService;
import lombok.AllArgsConstructor;
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
@Api(tags = "Links Slackbot", description = "The Links Slackbot API")
@RequestMapping(value = "/v1/commands/links")
@AllArgsConstructor
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
    @Value("${message.hide.link.instant}")
    private String messageHideLinkInstant;
    @Value("${message.hide.link.delayed}")
    private String messageHideLinkDelayed;

    @ApiOperation(
            value = "Save new link in links storage",
            notes = "Returns a message with saved Link id or Exception message",
            response = RichMessage.class, tags = {}
    )
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "The link has been successfully saved"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_METHOD, message = "Bad method"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNSUPPORTED_TYPE, message = "Unsupported request media type"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found")
    })
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void onReceiveSlashCommandSaveLink(@ApiParam(value = "Valid slack token", required = true)
                                              @RequestParam("token") String token,
                                              @ApiParam(value = "Command text", required = true)
                                              @RequestParam("text") String text,
                                              @ApiParam(value = "Slack responce URL", required = true)
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

    @ApiOperation(
            value = "Hide user's link",
            notes = "Returns a message with hidden Link id or Exception message",
            response = RichMessage.class, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "The link has been successfully hidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_METHOD, message = "Bad method"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNSUPPORTED_TYPE, message = "Unsupported request media type"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found")
    })
    @PostMapping(value = "/hide", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void onReceiveSlashCommandHideLink(@ApiParam(value = "Valid slack token", required = true)
                                              @RequestParam("token") String token,
                                              @ApiParam(value = "Command text", required = true)
                                              @RequestParam("text") String text,
                                              @ApiParam(value = "Slack responce URL", required = true)
                                              @RequestParam("response_url") String responseUrl,
                                              @ApiParam(value = "User that used command", required = true)
                                              @RequestParam("user_id") String fromSlackUser,
                                              HttpServletResponse response) throws IOException {
        exceptionsHandler.setResponseUrl(responseUrl);
        if (isRequestCorrect(token, response, fromSlackUser, responseUrl)) {
            sendInstantResponseMessage(response, messageHideLinkInstant);
            Link link = linksSlackbotService.hideLink(fromSlackUser, text);
            RichMessage message = new RichMessage(String.format(messageHideLinkDelayed, link.getId()));
            sendDelayedResponseMessage(responseUrl, message);
            log.info("'Hide link' command processed : text: '{}', response_url: '{}' and sent " +
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
        log.debug("After checking slack request parameters. Parameters are correct");
        return true;
    }
}

