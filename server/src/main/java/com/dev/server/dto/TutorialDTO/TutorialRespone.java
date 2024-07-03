package com.dev.server.dto.TutorialDTO;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class TutorialRespone {
    private Long id;
    private String title;
    private String description;
    private boolean published;
}
