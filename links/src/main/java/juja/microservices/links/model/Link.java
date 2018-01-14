package juja.microservices.links.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Ivan Shapovalov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class Link {

    @ApiModelProperty(value = "Id of saved link")
    private String id;
    @NotEmpty
    @ApiModelProperty(value = "URL of saved link", required = true)
    private String url;

    @ApiModelProperty(value = "Flag that means link is hidden or active")
    private boolean hidden;

    public Link(String url) {
        this.url = url;
    }
}