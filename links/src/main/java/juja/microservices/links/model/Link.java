package juja.microservices.links.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class Link {
    @Id
    private String id;
    @NotEmpty
    private String url;

    public Link(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return String.format("Link[id='%s', url='%s']", id, url);
    }
}
