package com.kwizera.springbootlab13trackerbooster.domain.dtos;

import jakarta.validation.constraints.*;

public record UserSignupDTO(
        @Pattern(regexp = "^[A-Za-z0-9 ]{2,50}$", message = "Name must contain only alphanumeric characters")
        @NotBlank(message = "Project name is required")
        String firstName,
        @Pattern(regexp = "^[A-Za-z0-9 ]{2,50}$", message = "Name must contain only alphanumeric characters")
        @NotBlank(message = "Project name is required")
        String lastName,
        @NotNull(message = "Email is required")
        @Email(message = "Invalid email")
        String email,
        @Size(min = 8, message = "The needs to be at least 8 characters")
        String password
) {
}
