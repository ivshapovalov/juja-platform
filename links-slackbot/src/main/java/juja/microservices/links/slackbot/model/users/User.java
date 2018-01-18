package juja.microservices.links.slackbot.model.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ivan Shapovalov
 */
@Getter
@AllArgsConstructor
public class User {
    @JsonProperty
    private String uuid;
    @JsonProperty("slackId")
    private String slackUser;
}
