package juja.microservices.links.slackbot.model.links;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ivan Shapovalov
 */
@AllArgsConstructor
@Getter
public class HideLinkRequest {

    private final String owner;

    private final String id;

}
