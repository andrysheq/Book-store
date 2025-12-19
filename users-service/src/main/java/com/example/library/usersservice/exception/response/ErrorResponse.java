package com.example.library.usersservice.exception.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * DTO для ошибки (используется в GlobalExceptionHandler)
 */
@Builder
@Schema(description = "Информация об ошибке")
public record ErrorResponse(
        @Schema(description = "HTTP статус ошибки", requiredMode = Schema.RequiredMode.REQUIRED, example = "404")
        int status,

        @Schema(description = "Код ошибки", requiredMode = Schema.RequiredMode.REQUIRED, example = "NOT_FOUND")
        String errorCode,

        @Schema(description = "Сообщение об ошибке", requiredMode = Schema.RequiredMode.REQUIRED, example = "Книга с ID 1 не найдена")
        String message,

        @Schema(description = "Дополнительные детали ошибки")
        String details,

        @Schema(description = "Путь запроса", requiredMode = Schema.RequiredMode.REQUIRED, example = "/book/1")
        String path,

        @Schema(description = "Время ошибки", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime timestamp
) {
}