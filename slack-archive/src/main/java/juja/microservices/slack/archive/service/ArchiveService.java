package juja.microservices.slack.archive.service;

import juja.microservices.slack.archive.model.Message;
import juja.microservices.slack.archive.model.MessagesRequest;

import java.util.List;

public interface ArchiveService {
    List<Message> getMessages(MessagesRequest request);

    List<String> getChannels();

    void saveMessage(Message message);
}
