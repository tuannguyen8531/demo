package com.dev.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.dev.server.util.MessageConstant;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, MessageConstant.UNCATEGORIZED_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR),
    RESOURCE_NOT_FOUND(1001, MessageConstant.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND),
    RESOURCE_ALREADY_EXISTS(1002, MessageConstant.RESOURCE_ALREADY_EXISTS, HttpStatus.CONFLICT),
    INVALID_LOGIN(1003, MessageConstant.INVALID_LOGIN, HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN(1004, MessageConstant.INVALID_REFRESH_TOKEN, HttpStatus.UNAUTHORIZED),
    USERNAME_EXISTED(1005, MessageConstant.USERNAME_EXISTED, HttpStatus.CONFLICT),
    EMAIL_EXISTED(1006, MessageConstant.EMAIL_EXISTED, HttpStatus.CONFLICT),
    UNAUTHORIZED(1007, MessageConstant.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
