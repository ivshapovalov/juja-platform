package juja.microservices.slack.archive.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
public class Message {
    @Id
    private String id;
    private String channel;
    private String text;
    private String team;
    private String type;
    private String user;
    private Date time;

    @JsonCreator
    public Message(
            @JsonProperty("channel") String channel,
            @JsonProperty("text") String text,
            @JsonProperty("team") String team,
            @JsonProperty("type") String type,
            @JsonProperty("user") String user,
            @JsonProperty("time") Date time) {
        this.channel = channel;
        this.text = text;
        this.team = team;
        this.type = type;
        this.user = user;
        this.time = time;
    }
}
