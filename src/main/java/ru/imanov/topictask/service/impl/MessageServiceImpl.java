package ru.imanov.topictask.service.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.imanov.topictask.exception.common.InvalidIdException;
import ru.imanov.topictask.exception.common.NullFieldException;
import ru.imanov.topictask.exception.message.MessageNotFoundException;
import ru.imanov.topictask.repository.MessageRepository;
import ru.imanov.topictask.repository.TopicRepository;
import ru.imanov.topictask.service.MessageService;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final TopicRepository topicRepository;

    @Override
    @Transactional
    public void delete(String id) {

        checkId(id);

        if (!messageRepository.existsById(id)) {
            throw new MessageNotFoundException("сообщение с id = " + id + " не найдено");
        }

        if (topicRepository.countMessagesOfTopic(messageRepository.findById(id).get().getTopic().getId()) == 1) {
            throw new NullFieldException("Нельзя удалить последнее сообщение топика");
        }

        messageRepository.deleteMessageById(id);
    }

    public void checkId(String id) {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidIdException("передан некорректный id");
        }
    }
}
