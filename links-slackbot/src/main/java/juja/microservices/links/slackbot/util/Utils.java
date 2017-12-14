package juja.microservices.links.slackbot.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import juja.microservices.links.slackbot.exceptions.ApiError;

import java.io.IOException;
import java.util.Collections;

/**
 * @author Ivan Shapovalov
 */
public class Utils {

    public static <T> void checkNull(T parameter, String message) {
        if (parameter == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static ApiError convertToApiError(String message) {
        int contentExists = message.indexOf("content:");
        if (contentExists != -1) {
            String apiMessage = message.substring(contentExists + 8);
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(apiMessage, ApiError.class);
            } catch (IOException ex) {
                message = ex.getMessage();
            }
        }
        return new ApiError(
                500, "BotInternalError",
                "I'm, sorry. I cannot parse api error message from remote service :(",
                "Cannot parse api error message from remote service",
                message, Collections.singletonList(message));
    }
}
