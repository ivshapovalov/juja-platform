package juja.microservices.links.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
@EqualsAndHashCode
public class Link {
    @Id
    private String id = "";
    private String linkURL;

    @Override
    public String toString() {
        return String.format(
                "Link[id=%s, URL='%s']",
                id, linkURL);
    }
}
