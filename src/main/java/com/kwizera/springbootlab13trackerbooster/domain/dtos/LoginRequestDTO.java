package com.kwizera.springbootlab13trackerbooster.domain.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record LoginRequestDTO(
        @Email(message = "Invalid email")
        String email,
        @NotEmpty(message = "Invalid password")
        String password
) {
}
