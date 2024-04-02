package ru.imanov.topictask.service;


import ru.imanov.topictask.entity.Message;
import ru.imanov.topictask.rest.dto.MessageWithoutTopicDto;
import ru.imanov.topictask.rest.dto.TopicAllFieldsDto;

public interface MessageService {
   void delete(String id);
}
