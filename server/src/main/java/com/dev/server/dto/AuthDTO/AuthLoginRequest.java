package com.dev.server.dto.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AuthLoginRequest {
    private String username;
    private String password;
}
