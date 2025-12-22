package com.example.library.model.contract.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * DTO запроса на блокировку книги
 */
public record BlockBookRequest(
        @Schema(
                description = "ID причины блокировки)",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "1"
        )
        @NotNull(message = "Причина блокировки обязательна")
        Integer reasonId,

        @Schema(
                description = "Комментарий модератора к блокировке книги",
                example = "Технические проблемы"
        )
        String comment
) {
}