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
    private String URL;

    @JsonCreator
    public SaveLinkRequest(@JsonProperty("URL") String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return String.format("Link[URL='%s']", URL);
    }
}
