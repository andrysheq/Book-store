package com.example.library.usersservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request DTO для авторизации пользователя
 */
public record AuthRequest(
        @JsonProperty("email")
        String email,

        @JsonProperty("password")
        String password
) {
}
