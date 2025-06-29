package com.kwizera.springbootlab13trackerbooster.domain.dtos;

import com.kwizera.springbootlab13trackerbooster.domain.enums.UserRole;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Builder
public record UserDTO(
        UUID id,
        String names,
        String email,
        UserRole role,
        List<String> skills,
        List<TaskDTO> tasks
) implements Serializable {
}
