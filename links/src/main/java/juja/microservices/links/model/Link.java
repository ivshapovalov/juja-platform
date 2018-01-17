package juja.microservices.links.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

@ApiModel
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
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

    public Link(String url, String owner) {
        this.url = url;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return String.format("Link[id='%s', owner='%s', url='%s']", id, owner, url);
    }
}
