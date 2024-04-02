package ru.imanov.topictask.rest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewTopicRequestDto {

    private String topicName;

    private MessageWithoutTopicDto message;
}
