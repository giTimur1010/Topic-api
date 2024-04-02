package ru.imanov.topictask.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TopicAllFieldsDto {
    private String id;

    private String name;

    private String created;

    private List<MessageWithoutTopicDto> messages;
}
