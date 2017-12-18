package juja.microservices.slack.archive.service.impl;

import juja.microservices.slack.archive.model.Channel;
import juja.microservices.slack.archive.model.ChannelDTO;
import juja.microservices.slack.archive.model.Message;
import juja.microservices.slack.archive.model.MessagesRequest;
import juja.microservices.slack.archive.repository.ArchiveRepository;
import juja.microservices.slack.archive.service.ArchiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArchiveServiceImpl implements ArchiveService {

    private final ArchiveRepository repository;

    @Inject
    public ArchiveServiceImpl(ArchiveRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Message> getMessages(MessagesRequest request) {
        //TODO Should be implemented
        return null;
    }

    @Override
    public List<ChannelDTO> getChannels() {
        List<ChannelDTO> channels = repository.getChannels().stream()
                .map(this::convertChannelToChannelDTO)
                .collect(Collectors.toList());
        log.debug("returned list of channels: {}", channels.toString());
        return channels;
    }

    private ChannelDTO convertChannelToChannelDTO(Channel channel) {
        return new ChannelDTO(channel.getChannelId(), channel.getChannelName());
    }

    @Override
    public void saveMessage(Message message) {
        //TODO Should be implemented
    }
}
