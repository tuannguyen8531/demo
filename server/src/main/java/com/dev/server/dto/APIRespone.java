package com.dev.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIRespone<T> {
    private boolean success;
    private String message;
    private Integer error_code;
    private T data;

    public APIRespone() {}

    public APIRespone(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public APIRespone(boolean success, String message, int error_code) {
        this.success = success;
        this.message = message;
        this.error_code = error_code;
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getError_code() {
        return error_code;
    }
    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
