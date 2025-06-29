package com.kwizera.springbootlab13trackerbooster.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "Test Controller", description = "This controller tests if the user is being authenticated")
public class AuthTestController {
    @GetMapping("/public")
    public Map<String, String> publicEndpoint() {
        return Map.of(
                "message", "This is a public endpoint",
                "timestamp", String.valueOf(System.currentTimeMillis())
        );
    }

    @GetMapping("/protected")
    public Map<String, String> protectedEndpoint() {
        return Map.of(
                "message", "This is a protected endpoint - you are authenticated!",
                "timestamp", String.valueOf(System.currentTimeMillis())
        );
    }
}
