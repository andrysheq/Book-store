package com.example.library.model.contract.book;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO полной информации о книге с рецензиями
 */
@Schema(description = "Полная информация о книге с рецензиями для модератора")
public record BookDetailView(
        @Schema(description = "Идентификатор книги", requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,

        @Schema(description = "Название книги", requiredMode = Schema.RequiredMode.REQUIRED)
        String title,

        @Schema(description = "Цена в копейках", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer price,

        @Schema(description = "Статус книги", requiredMode = Schema.RequiredMode.REQUIRED)
        Object status,

        @Schema(description = "Информация об авторе", requiredMode = Schema.RequiredMode.REQUIRED)
        AuthorInfo author,

        @Schema(description = "Описание книги", requiredMode = Schema.RequiredMode.REQUIRED)
        String description,

        @Schema(description = "Информация о жанре", requiredMode = Schema.RequiredMode.REQUIRED)
        GenreInfo genre,

        @Schema(description = "Причина блокировки (если заблокирована)")
        String blockingReason,

        @Schema(description = "Список рецензий на книгу")
        List<ReviewInfo> reviews
) {
    @Schema(description = "Информация об авторе")
    public record AuthorInfo(
            @Schema(description = "Идентификатор автора", requiredMode = Schema.RequiredMode.REQUIRED)
            Integer id,

            @Schema(description = "Имя автора", requiredMode = Schema.RequiredMode.REQUIRED)
            String firstName,

            @Schema(description = "Фамилия автора", requiredMode = Schema.RequiredMode.REQUIRED)
            String lastName,

            @Schema(description = "Email автора", requiredMode = Schema.RequiredMode.REQUIRED)
            String email
    ) {
    }

    @Schema(description = "Информация о жанре")
    public record GenreInfo(
            @Schema(description = "Идентификатор жанра", requiredMode = Schema.RequiredMode.REQUIRED)
            Integer id,

            @Schema(description = "Наименование жанра", requiredMode = Schema.RequiredMode.REQUIRED)
            String name
    ) {
    }

    @Schema(description = "Информация о рецензии")
    public record ReviewInfo(
            @Schema(description = "Идентификатор рецензии", requiredMode = Schema.RequiredMode.REQUIRED)
            Long id,

            @Schema(description = "Текст рецензии")
            String content,

            @Schema(description = "Рейтинг (от 1 до 5)", requiredMode = Schema.RequiredMode.REQUIRED)
            Integer rating,

            @Schema(description = "Статус рецензии", requiredMode = Schema.RequiredMode.REQUIRED)
            String status,

            @Schema(description = "Имя автора рецензии", requiredMode = Schema.RequiredMode.REQUIRED)
            String authorName,

            @Schema(description = "Дата и время создания рецензии", requiredMode = Schema.RequiredMode.REQUIRED)
            LocalDateTime createdAt
    ) {
    }
}
