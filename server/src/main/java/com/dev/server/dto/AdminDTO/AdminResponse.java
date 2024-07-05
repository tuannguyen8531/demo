package com.dev.server.dto.AdminDTO;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class AdminResponse {
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String role;
    private String roleMenu;
}
