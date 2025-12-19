package com.example.library.model.contract.review;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Полная информация о рецензии для модератора")
public record ReviewDetailView(
        @Schema(description = "Идентификатор рецензии", requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,

        @Schema(description = "Текст рецензии")
        String content,

        @Schema(description = "Рейтинг (от 1 до 5)", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer rating,

        @Schema(description = "Статус рецензии", requiredMode = Schema.RequiredMode.REQUIRED)
        String status,

        @Schema(description = "Информация о книге, на которую оставлена рецензия", requiredMode = Schema.RequiredMode.REQUIRED)
        BookInfo book,

        @Schema(description = "Информация об авторе рецензии", requiredMode = Schema.RequiredMode.REQUIRED)
        AuthorInfo author,

        @Schema(description = "Причина отклонения рецензии (если отклонена)")
        String rejectionReason,

        @Schema(description = "Комментарий модератора к отклонению")
        String rejectionComment,

        @Schema(description = "Дата и время создания рецензии", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime createdAt,

        @Schema(description = "Дата и время последнего изменения статуса")
        LocalDateTime statusUpdatedAt
) {
    @Schema(description = "Краткая информация о книге")
    public record BookInfo(
            @Schema(description = "Идентификатор книги", requiredMode = Schema.RequiredMode.REQUIRED)
            Long id,

            @Schema(description = "Название книги", requiredMode = Schema.RequiredMode.REQUIRED)
            String title
    ) {
    }

    @Schema(description = "Информация об авторе рецензии")
    public record AuthorInfo(
            @Schema(description = "Идентификатор пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
            Long id,

            @Schema(description = "Email пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
            String email,

            @Schema(description = "Имя пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
            String firstName
    ) {
    }
}