package ru.imanov.topictask.rest.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class MessageWithoutTopicDto {
    private String id;

    private String text;

    private String author;

    private String created;
}
