package com.api.ecomerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericException extends RuntimeException {

    private final ErrorCode errorCode;

    public GenericException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
