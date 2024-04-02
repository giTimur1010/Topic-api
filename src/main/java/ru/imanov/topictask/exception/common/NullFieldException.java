package ru.imanov.topictask.exception.common;

import org.springframework.http.HttpStatus;
import ru.imanov.topictask.exception.common.BaseException;

public class NullFieldException extends BaseException {
    public NullFieldException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
