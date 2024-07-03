package com.dev.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.dev.server.util.MessageConstant;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, MessageConstant.UNCATEGORIZED_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR),
    RESOURCE_NOT_FOUND(404, MessageConstant.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND),
    RESOURCE_ALREADY_EXISTS(409, MessageConstant.RESOURCE_ALREADY_EXISTS, HttpStatus.CONFLICT);


    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
