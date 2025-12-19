package com.example.library.model.contract.book;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * DTO списка книг для модератора (реестр)
 */
@Schema(description = "Информация о книге в реестре модератора")
public record BookRegistryView(
        @Schema(description = "Идентификатор книги", requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,

        @Schema(description = "Название книги", requiredMode = Schema.RequiredMode.REQUIRED)
        String title,

        @Schema(description = "Цена в копейках", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer price,

        @Schema(description = "Статус книги (В наличии / Заблокирована)", requiredMode = Schema.RequiredMode.REQUIRED)
        String status,

        @Schema(description = "ФИО автора", requiredMode = Schema.RequiredMode.REQUIRED)
        String authorName,

        @Schema(description = "Наименование жанра", requiredMode = Schema.RequiredMode.REQUIRED)
        String genre,

        @Schema(description = "Причина блокировки (если заблокирована)")
        String blockingReason
) {
}
