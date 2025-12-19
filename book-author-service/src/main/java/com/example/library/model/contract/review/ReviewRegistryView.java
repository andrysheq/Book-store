package com.example.library.model.contract.review;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * DTO списка рецензий для модератора (реестр)
 */
@Schema(description = "Информация о рецензии в реестре модератора")
public record ReviewRegistryView(
        @Schema(description = "Идентификатор рецензии", requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,

        @Schema(description = "Текст рецензии")
        String content,

        @Schema(description = "Рейтинг (от 1 до 5)", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer rating,

        @Schema(description = "Статус рецензии (На рассмотрении / Подтвержден / Отклонен)", requiredMode = Schema.RequiredMode.REQUIRED)
        String status,

        @Schema(description = "Название книги, на которую оставлена рецензия", requiredMode = Schema.RequiredMode.REQUIRED)
        String bookTitle,

        @Schema(description = "Email автора рецензии", requiredMode = Schema.RequiredMode.REQUIRED)
        String authorEmail,

        @Schema(description = "Причина отклонения рецензии (если отклонена)")
        String rejectionReason,

        @Schema(description = "Дата и время создания рецензии", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime createdAt,

        @Schema(description = "Дата и время последнего изменения статуса")
        LocalDateTime statusUpdatedAt
) {
}
