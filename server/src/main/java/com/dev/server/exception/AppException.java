package com.dev.server.exception;

public class AppException extends RuntimeException {
    private int statusCode;

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
