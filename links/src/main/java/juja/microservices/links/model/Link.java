package juja.microservices.links.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

/**
 * @author Ivan Shapovalov
 */
@Getter
@EqualsAndHashCode
public class Link {
    @Id
    private String id;
    @NotEmpty
    private String URL;

    public Link(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return String.format("{_id=%s, URL=%s}", id, URL);
    }
}