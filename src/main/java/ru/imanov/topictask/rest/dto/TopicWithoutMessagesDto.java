package ru.imanov.topictask.rest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TopicWithoutMessagesDto {
    private String id;

    private String name;

    private String created;

}
