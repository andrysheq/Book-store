package com.example.library.usersservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response DTO для авторизации (содержит токен)
 */
public record AuthResponse(
        @JsonProperty("token")
        String token,

        @JsonProperty("user")
        UserResponse user
) {
}
