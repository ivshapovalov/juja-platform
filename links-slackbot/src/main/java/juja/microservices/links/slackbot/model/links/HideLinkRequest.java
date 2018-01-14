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
public class HideLinkRequest {

    @ApiModelProperty(value = "A person who saves this link, used to identify while changing/archiving", required = true)
    private final String owner;

    @ApiModelProperty(value = "A unique id of the link, used to identify", required = true)
    private final String id;

}
