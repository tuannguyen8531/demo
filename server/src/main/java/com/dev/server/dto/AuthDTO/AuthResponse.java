package com.dev.server.dto.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AuthResponse {
    private String access_token;
    private String token_type = "Bearer";
    private long expires_in;
    private String refresh_token;

    public AuthResponse(String access_token, long expires_in, String refresh_token) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
    }
}
