package com.kwizera.springbootlab13trackerbooster.domain.dtos;

import lombok.Builder;

@Builder
public record LoginResponse(
        String names,
        String email,
        String token
) {
}
