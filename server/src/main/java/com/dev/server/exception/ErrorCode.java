package com.dev.server.exception;

public enum ErrorCode {
    RESOURCE_NOT_FOUND(404, "Resource not found"),
    RESOURCE_ALREADY_EXISTS(409, "Resource already exists"),
    FIELD_REQUIRED(400, "Missing required field"),;


    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
