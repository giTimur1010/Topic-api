package ru.imanov.topictask.service;

import org.springframework.data.domain.Pageable;
import ru.imanov.topictask.rest.dto.MessageWithoutTopicDto;
import ru.imanov.topictask.rest.dto.NewTopicRequestDto;
import ru.imanov.topictask.rest.dto.TopicAllFieldsDto;
import ru.imanov.topictask.rest.dto.TopicWithoutMessagesDto;

import java.util.List;

public interface TopicService {
    TopicAllFieldsDto add(NewTopicRequestDto request);
    TopicAllFieldsDto update(TopicWithoutMessagesDto request);

    List<TopicWithoutMessagesDto> getAll(Integer offset, Integer limit);

    TopicAllFieldsDto getById(String id);

    TopicAllFieldsDto addMessage(String topicId, MessageWithoutTopicDto message);

    TopicAllFieldsDto updateMessage(String topicId, MessageWithoutTopicDto message);

}
