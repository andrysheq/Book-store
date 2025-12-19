package com.example.library.model.contract.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

/**
 * DTO запроса на блокировку книги
 */
public record BlockBookRequest(
        @JsonProperty("reason_id")
        @NotNull(message = "Причина блокировки обязательна")
        Integer reasonId,

        @JsonProperty("comment")
        String comment
) {
}