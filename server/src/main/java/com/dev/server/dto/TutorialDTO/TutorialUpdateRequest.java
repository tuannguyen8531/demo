package com.dev.server.dto.TutorialDTO;

import com.dev.server.util.MessageConstant;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class TutorialUpdateRequest {
    @NotBlank(message = MessageConstant.TITLE_REQUIRED)
    @Size(min = 3, max = 100, message = MessageConstant.TITLE_LENGTH)
    private String title;

    @NotBlank(message = MessageConstant.DESCRIPTION_REQUIRED)
    @Size(min = 10, message = MessageConstant.DESCRIPTION_LENGTH)
    private String description;

    private boolean published;
}
