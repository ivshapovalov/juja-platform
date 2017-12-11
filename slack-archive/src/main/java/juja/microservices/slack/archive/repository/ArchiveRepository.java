package juja.microservices.slack.archive.repository;

import juja.microservices.slack.archive.model.Message;

import java.util.List;

public interface ArchiveRepository {
    void saveMessage(Message message);

    List<String> getChannels();

    List<Message> getMessages(String channel, int number);
}
