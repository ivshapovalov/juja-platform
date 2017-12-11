package juja.microservices.links.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
public class SaveLinkRequest {
    private String linkURL;

    @JsonCreator
    public SaveLinkRequest(@JsonProperty("linkURL") String linkURL) {
        this.linkURL = linkURL;
    }

    @Override
    public String toString() {
        return String.format("Link[URL='%s']", linkURL);
    }
}
