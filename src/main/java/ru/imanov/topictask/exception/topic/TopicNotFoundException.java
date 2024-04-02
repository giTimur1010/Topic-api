package ru.imanov.topictask.exception.topic;

import org.springframework.http.HttpStatus;
import ru.imanov.topictask.exception.common.BaseException;

public class TopicNotFoundException extends BaseException {
    public TopicNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
