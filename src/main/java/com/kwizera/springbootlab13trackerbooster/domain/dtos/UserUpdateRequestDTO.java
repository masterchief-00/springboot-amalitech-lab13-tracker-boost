package com.kwizera.springbootlab13trackerbooster.domain.dtos;

import com.kwizera.springbootlab13trackerbooster.domain.enums.UserRole;

import java.util.List;

public record UserUpdateRequestDTO(
        List<String> skills,
        UserRole role
) {
}
