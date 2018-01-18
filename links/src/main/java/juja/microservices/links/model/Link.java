package juja.microservices.links.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

/**
 * @author Ivan Shapovalov
 */

@ApiModel
@ToString
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Link {
    @Id
    @ApiModelProperty(value = "Unique ID of the link", required = true)
    private String id;
    @NotEmpty
    @ApiModelProperty(value = "Owner of the link", required = true)
    private String owner;
    @NotEmpty
    @ApiModelProperty(value = "URL of the link. No duplicates for owner+URL pairs", required = true)
    private String url;

    @ApiModelProperty(value = "Flag that means link is hidden or not")
    private boolean hidden;

    public Link(String owner, String url) {
        this.owner = owner;
        this.url = url;
    }

    public Link(String id, String owner, String url, boolean hidden) {
        this.id = id;
        this.owner = owner;
        this.url = url;
        this.hidden = hidden;
    }
}

