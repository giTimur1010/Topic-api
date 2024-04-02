package ru.imanov.topictask.exception.message;

import org.springframework.http.HttpStatus;
import ru.imanov.topictask.exception.common.BaseException;

public class MessageNotFoundException extends BaseException {

    public MessageNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
