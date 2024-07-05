package com.dev.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse<T> {
    private boolean success;
    private String message;
    private Integer error_code;
    private T data;

    public APIResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public APIResponse(boolean success, String message, int error_code) {
        this.success = success;
        this.message = message;
        this.error_code = error_code;
    }
}
