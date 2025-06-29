package com.kwizera.springbootlab13trackerbooster.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record CreateProjectDTO(
        @Pattern(regexp = "^[A-Za-z0-9 ]{2,50}$", message = "Name must contain only alphanumeric characters")
        @NotBlank(message = "Project name is required")
        String name,

        @Size(max = 150)
        @NotBlank(message = "Project description is required")
        String description,

        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "Invalid date")
        @NotBlank(message = "Deadline date is required")
        String deadline,

        @Size(min = 1, message = "At least one developer needs to be assigned")
        Set<UUID> developers

) {
}
