package ru.imanov.topictask.exception.common;

import org.springframework.http.HttpStatus;

public class InvalidIdException extends BaseException {
    public InvalidIdException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
