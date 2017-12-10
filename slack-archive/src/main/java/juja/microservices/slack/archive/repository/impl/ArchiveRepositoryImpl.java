package juja.microservices.slack.archive.repository.impl;

import juja.microservices.slack.archive.model.Message;
import juja.microservices.slack.archive.repository.ArchiveRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArchiveRepositoryImpl implements ArchiveRepository {
    @Override
    public void saveMessage(Message message) {
        //TODO Should be implemented
    }

    @Override
    public List<String> getChannels() {
        //TODO Should be implemented
        return null;
    }

    @Override
    public List<Message> getMessages(String channel, int number) {
        //TODO Should be implemented
        return null;
    }
}
