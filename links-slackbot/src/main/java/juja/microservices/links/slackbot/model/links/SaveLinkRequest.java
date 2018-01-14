package juja.microservices.links.slackbot.model.links;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ivan Shapovalov
 */
@AllArgsConstructor
@Getter
@ApiModel
public class SaveLinkRequest {

    @ApiModelProperty(value = "URL to save in Link", required = true)
    private final String url;

}
