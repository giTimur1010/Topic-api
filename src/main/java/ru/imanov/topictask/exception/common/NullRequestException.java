package ru.imanov.topictask.exception.common;

import org.springframework.http.HttpStatus;

public class NullRequestException extends BaseException {
    public NullRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
