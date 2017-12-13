package juja.microservices.links.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Getter
@EqualsAndHashCode
public class Link {
    @Id
    @NonNull
    private String id;
    @NonNull
    private String URL;

    @Override
    public String toString() {
        return String.format("Link[id=%s, URL='%s']", id, URL);
    }
}
