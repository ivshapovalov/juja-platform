package juja.microservices.links.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@EqualsAndHashCode
@ToString
@ApiModel
public class SaveLinkRequest {
    @NotEmpty
    @ApiModelProperty(value = "URL of saved link", required = true)
    private String url;

    @JsonCreator
    public SaveLinkRequest(@JsonProperty("url") String url) {
        this.url = url;
    }
}
