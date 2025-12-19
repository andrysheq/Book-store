package com.example.library.usersservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Response DTO для информации о пользователе
 */
public record UserResponse(
        @JsonProperty("id")
        Long id,

        @JsonProperty("email")
        String email,

        @JsonProperty("firstName")
        String firstName,

        @JsonProperty("lastName")
        String lastName,

        @JsonProperty("role")
        String role,

        @JsonProperty("isActive")
        Boolean isActive,

        @JsonProperty("createdAt")
        LocalDateTime createdAt,

        @JsonProperty("lastLoginAt")
        LocalDateTime lastLoginAt
) {
}
