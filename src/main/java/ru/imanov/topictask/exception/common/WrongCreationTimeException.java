package ru.imanov.topictask.exception.common;

import org.springframework.http.HttpStatus;

public class WrongCreationTimeException extends BaseException {
    public WrongCreationTimeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
