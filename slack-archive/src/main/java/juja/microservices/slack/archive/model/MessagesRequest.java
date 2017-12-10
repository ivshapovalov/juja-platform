package juja.microservices.slack.archive.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class MessagesRequest {
    @NonNull
    private String channel;
    @NonNull
    private int number;

    @JsonCreator
    public MessagesRequest(@JsonProperty("channel") String channel, @JsonProperty("number") int number) {
        this.channel = channel;
        this.number = number;
    }
}
