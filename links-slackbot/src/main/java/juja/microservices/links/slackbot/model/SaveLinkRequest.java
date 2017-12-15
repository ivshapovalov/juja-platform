package juja.microservices.links.slackbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ivan Shapovalov
 */
@AllArgsConstructor
@Getter
public class SaveLinkRequest {

    private final String url;

}
