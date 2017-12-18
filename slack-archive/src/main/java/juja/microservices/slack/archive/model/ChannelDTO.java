package juja.microservices.slack.archive.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChannelDTO {
    @JsonProperty
    private String id;
    @JsonProperty
    private String name;

    public ChannelDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
