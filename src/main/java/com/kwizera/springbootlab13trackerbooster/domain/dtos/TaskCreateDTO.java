package com.kwizera.springbootlab13trackerbooster.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.UUID;

@Builder
public record TaskCreateDTO(
        @Pattern(regexp = "^[A-Za-z0-9 ]{2,50}$", message = "Task title must contain only alphanumeric characters")
        @NotBlank(message = "Task title is required")
        String title,

        @Size(max = 150)
        @NotBlank(message = "Title description is required")
        String description,

        UUID developerId

) {
}
