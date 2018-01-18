package juja.microservices.links.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel
@Getter
@EqualsAndHashCode
@ToString
public class SaveLinkRequest {
    @NotEmpty
    @ApiModelProperty(value = "URL of the link to be saved", required = true)
    private String url;
    @NotEmpty
    @ApiModelProperty(value = "Person who saves the link", required = true)
    private String owner;

    @JsonCreator
    public SaveLinkRequest(@JsonProperty("owner") String owner, @JsonProperty("url") String url) {
        this.owner = owner;
        this.url = url;
    }

}
