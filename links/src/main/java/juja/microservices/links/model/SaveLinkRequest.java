package juja.microservices.links.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@EqualsAndHashCode
public class SaveLinkRequest {
    @NotEmpty
    private String url;

    @JsonCreator
    public SaveLinkRequest(@JsonProperty("url") String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return String.format("Link[url='%s']", url);
    }
}
