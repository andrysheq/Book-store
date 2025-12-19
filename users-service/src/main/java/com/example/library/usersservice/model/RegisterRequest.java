package com.example.library.usersservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request DTO для регистрации пользователя
 */
public record RegisterRequest(
        @JsonProperty("email")
        String email,

        @JsonProperty("password")
        String password,

        @JsonProperty("firstName")
        String firstName,

        @JsonProperty("lastName")
        String lastName
) {
}
