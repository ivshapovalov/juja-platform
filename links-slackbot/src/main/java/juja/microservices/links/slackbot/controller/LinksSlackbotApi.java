package juja.microservices.links.slackbot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @author Ivan Shapovalov
 */
@Api(value = "Links Slackbot", description = "The Links Slackbot API")
public interface LinksSlackbotApi {

    @ApiOperation(
            value = "Save new link in links storage",
            notes = "Returns a message with hidden Link id or Exception message",
            response = RichMessage.class, tags = {}
    )
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully saved new link"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_METHOD, message = "Bad method"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNSUPPORTED_TYPE, message = "Unsupported request media type")
    })
    @PostMapping(value = "/links/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void onReceiveSlashCommandSaveLink(String token,
                                       String text,
                                       String responseUrl,
                                       HttpServletResponse response) throws IOException;

    @ApiOperation(
            value = "Hide user's link",
            notes = "Returns a message with hidden Link id or Exception message",
            response = RichMessage.class, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully hid new link"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_METHOD, message = "Bad method"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNSUPPORTED_TYPE, message = "Unsupported request media type")
    })
    @PostMapping(value = "/links/hide", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void onReceiveSlashCommandHideLink(String token,
                                       String text,
                                       String fromSlackUser,
                                       String responseUrl,
                                       HttpServletResponse response) throws IOException;
}
