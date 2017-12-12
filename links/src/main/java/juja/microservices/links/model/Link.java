package juja.microservices.links.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

@Getter
@EqualsAndHashCode
public class Link {
    @Id
    private String id;
    @NotEmpty
    private String URL;

    @Override
    public String toString() {
        return String.format("Link[id=%s, URL='%s']", id, URL);
    }
}
