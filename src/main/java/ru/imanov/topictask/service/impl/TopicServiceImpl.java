package ru.imanov.topictask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.imanov.topictask.entity.Message;
import ru.imanov.topictask.entity.Topic;
import ru.imanov.topictask.exception.common.InvalidIdException;
import ru.imanov.topictask.exception.common.NullRequestException;
import ru.imanov.topictask.exception.common.WrongCreationTimeException;
import ru.imanov.topictask.exception.message.MessageAlreadyInDatabaseException;
import ru.imanov.topictask.exception.common.NullFieldException;
import ru.imanov.topictask.exception.message.MessageNotFoundException;
import ru.imanov.topictask.exception.topic.TopicNotFoundException;
import ru.imanov.topictask.repository.MessageRepository;
import ru.imanov.topictask.repository.TopicRepository;
import ru.imanov.topictask.rest.dto.MessageWithoutTopicDto;
import ru.imanov.topictask.rest.dto.NewTopicRequestDto;
import ru.imanov.topictask.rest.dto.TopicAllFieldsDto;
import ru.imanov.topictask.rest.dto.TopicWithoutMessagesDto;
import ru.imanov.topictask.service.TopicService;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    private final MessageRepository messageRepository;

    @Override
    public TopicAllFieldsDto add(NewTopicRequestDto request) {

        if (request == null) {
            throw new NullRequestException("Топик в запросе отсутствует");
        }

        MessageWithoutTopicDto messageFromRequest = request.getMessage();

        if (messageFromRequest == null) {
            throw new NullFieldException("сообщение в запросе отсутствует");
        }

        checkId(messageFromRequest.getId());

        if (request.getTopicName() == null) {
            throw new NullFieldException("имя топика отсутствует");
        }

        if (messageRepository.existsById(messageFromRequest.getId())) {
            throw new MessageAlreadyInDatabaseException("При создании топика было сообщение с id, " +
                    "которое уже есть в базе данных. id = " + messageFromRequest.getId());
        }

        checkTime(messageFromRequest.getCreated());

        if (messageFromRequest.getText() == null) {
            throw new NullFieldException("текст сообщения отсутствует");
        }

        Message message = Message.builder()
                .id(messageFromRequest.getId())
                .text(messageFromRequest.getText())
                .author(messageFromRequest.getAuthor())
                .build();

        message.setCreated(messageFromRequest.getCreated());

        Topic topic = Topic.builder()
                .name(request.getTopicName())
                .messages(Arrays.asList(message))
                .build();

        topic.setCreated(ZonedDateTime.now().toString());

        message.setTopic(topic);

        Topic savedTopic = topicRepository.save(topic);

        return topicToDto(savedTopic);

    }

    @Override
    public TopicAllFieldsDto update(TopicWithoutMessagesDto request) {

        if (request == null) {
            throw new NullRequestException("Топик в запросе отсутствует");
        }

        checkId(request.getId());

        if (!topicRepository.existsById(request.getId())) {
            throw new TopicNotFoundException("Топик с id = " + request.getId() + " не найден");
        }

        checkTime(request.getCreated());

        if (request.getName() == null) {
            throw new NullFieldException("имя топика отсутствует");
        }

        Topic topic = topicRepository.findById(request.getId()).get();

        topic.setName(request.getName());
        topic.setCreated(request.getCreated());

        Topic savedTopic = topicRepository.save(topic);

        return topicToDto(savedTopic);
    }

    @Override
    public List<TopicWithoutMessagesDto> getAll(Integer offset, Integer limit) {
        if (offset == null) {
            throw new NullRequestException("offset отсутствует в запросе");
        }

        if (limit == null) {
            throw new NullRequestException("limit отсутствует в запросе");
        }


        Page<Topic> page = topicRepository.findAll(PageRequest.of(offset, limit));
        return page.getContent().stream()
                .map(topic -> TopicWithoutMessagesDto.builder()
                        .id(topic.getId())
                        .name(topic.getName())
                        .created(topic.getCreated())
                        .build()
                )
                .toList();
    }

    @Override
    public TopicAllFieldsDto getById(String id) {
        checkId(id);

        if (!topicRepository.existsById(id)) {
            throw new TopicNotFoundException("Топик с id = " + id + "не найден");
        }

        return topicToDto(topicRepository.findById(id).get());
    }

    @Override
    public TopicAllFieldsDto addMessage(String topicId, MessageWithoutTopicDto messageFromRequest) {

        if (messageFromRequest == null) {
            throw new NullFieldException("сообщение в запросе отсутствует");
        }

        checkId(topicId);
        checkId(messageFromRequest.getId());

        if (!topicRepository.existsById(topicId)) {
            throw new TopicNotFoundException("Топик с id = " + topicId + " не найден");
        }

        if (messageRepository.existsById(messageFromRequest.getId())) {
            throw new MessageAlreadyInDatabaseException("сообщение с id =  " + messageFromRequest.getId() + "уже есть в базе данных");
        }

        checkTime(messageFromRequest.getCreated());

        if (messageFromRequest.getText() == null) {
            throw new NullFieldException("сообщение в запросе отсутствует");
        }

        Message message = Message.builder()
                .id(messageFromRequest.getId())
                .text(messageFromRequest.getText())
                .author(messageFromRequest.getAuthor())
                .build();
        message.setCreated(messageFromRequest.getCreated());


        Topic topic = topicRepository.findById(topicId).get();

        topic.getMessages().add(message);

        message.setTopic(topic);

        Topic savedTopic = topicRepository.save(topic);

        return topicToDto(savedTopic);
    }

    @Override
    public TopicAllFieldsDto updateMessage(String topicId, MessageWithoutTopicDto message) {


        if (message == null) {
            throw new NullFieldException("сообщение в запросе отсутствует");
        }

        checkTime(message.getCreated());

        if (message.getText() == null) {
            throw new NullFieldException("текст у сообщения отсутствует");
        }


        checkId(topicId);
        checkId(message.getId());

        if (!topicRepository.existsById(topicId)) {
            throw new TopicNotFoundException("Топик с id = " + topicId + " не найден");
        }

        if (!messageRepository.existsById(message.getId())) {
            throw new MessageNotFoundException("Сообщение с id = " + topicId + " не найдено");
        }

        Message messageFromDb = messageRepository.findById(message.getId()).get();

        if (!messageFromDb.getTopic().getId().equals(topicId)) {
            throw new InvalidIdException("Сообщение принадлежит другому топику");
        }

        messageFromDb.setText(message.getText());
        messageFromDb.setAuthor(message.getAuthor());
        messageFromDb.setCreated(message.getCreated());

        messageRepository.save(messageFromDb);

        return topicToDto(topicRepository.findById(topicId).get());
    }

    public void checkTime(String time) {
        try {
            ZonedDateTime dateTime = ZonedDateTime.parse(time);

            if (dateTime.isAfter(ZonedDateTime.now())) {
                throw new WrongCreationTimeException("было указано время создания которое еще не наступило. " +
                        "Указанное время создания: " + time);
            }

        } catch (DateTimeParseException e) {
            throw new WrongCreationTimeException(e.getMessage());
        }
    }

    public void checkId(String id) {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidIdException("передан некорректный id");
        }
    }

    public TopicAllFieldsDto topicToDto(Topic topic) {

        return TopicAllFieldsDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .created(topic.getCreated())
                .messages(
                        topic.getMessages().stream()
                                .map(message ->
                                    MessageWithoutTopicDto.builder()
                                            .id(message.getId())
                                            .text(message.getText())
                                            .author(message.getAuthor())
                                            .created(message.getCreated())
                                            .build()
                                )
                                .toList()

                )
                .build();
    }
}
