package com.dev.server.dto.AuthDTO;

import com.dev.server.util.MessageConstant;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AuthRegisterRequest {
    @NotBlank(message = MessageConstant.FULL_NAME_REQUIRED)
    @Size(min = 2, max = 100, message = MessageConstant.FULL_NAME_LENGTH)
    private String fullName;

    @NotBlank(message = MessageConstant.USERNAME_REQUIRED)
    @Size(min = 5, max = 20, message = MessageConstant.USERNAME_LENGTH)
    @Pattern(regexp = "^[a-zA-Z]([_](?![_])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$", message = MessageConstant.USERNAME_PATTERN)
    private String username;

    @Email(message = MessageConstant.EMAIL_INVALID)
    private String email;

    private String password;
}
