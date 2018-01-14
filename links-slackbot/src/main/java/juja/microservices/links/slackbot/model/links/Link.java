package juja.microservices.links.slackbot.model.links;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author Ivan Shapovalov
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Link {

    private final String id;

    private final String url;

}
