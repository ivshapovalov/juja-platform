package juja.microservices.links.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

/**
 * @author Ivan Shapovalov
 */
@Data
@ApiModel
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Link {
    @Id
    @ApiModelProperty(value = "Unique ID of the link", required = true)
    private String id;
    @NotEmpty
    @ApiModelProperty(value = "URL of the link. No duplicates for owner+URL pairs", required = true)
    private String url;
    @NotEmpty
    @ApiModelProperty(value = "Owner of the link", required = true)
    private String owner;

    @ApiModelProperty(value = "Flag that means link is hidden or not")
    private boolean hidden;

    public Link(String url, String owner) {
        this.url = url;
        this.owner = owner;
    }
}
