package juja.microservices.links.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Link {
    @Id
    @NonNull
    private String id;
    @NonNull
    private String url;

    @Override
    public String toString() {
        return String.format("Link[id=%s, url='%s']", id, url);
    }
}
