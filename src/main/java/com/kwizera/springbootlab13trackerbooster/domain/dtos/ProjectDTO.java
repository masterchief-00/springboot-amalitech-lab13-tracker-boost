package com.kwizera.springbootlab13trackerbooster.domain.dtos;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ProjectDTO(
        UUID id,
        String name,
        String description,
        List<UserDTO> developer,
        List<TaskDTO> tasks
) {
}
