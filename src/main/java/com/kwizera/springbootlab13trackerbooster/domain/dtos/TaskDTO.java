package com.kwizera.springbootlab13trackerbooster.domain.dtos;

import com.kwizera.springbootlab13trackerbooster.domain.enums.TaskStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record TaskDTO(
        UUID id,
        String title,
        String description,
        String projectName,
        TaskStatus status
) {
}
