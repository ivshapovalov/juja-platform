package juja.microservices.links.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

/**
 * @author Ivan Shapovalov
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Link {
    @Id
    private String id;
    @NotEmpty
    private String url;

    @Override
    public String toString() {
        return String.format("{_id=%s, url=%s}", id, url);
    }
}