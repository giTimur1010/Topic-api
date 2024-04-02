package ru.imanov.topictask.exception.message;

import org.springframework.http.HttpStatus;
import ru.imanov.topictask.exception.common.BaseException;

public class MessageAlreadyInDatabaseException extends BaseException {

    public MessageAlreadyInDatabaseException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
