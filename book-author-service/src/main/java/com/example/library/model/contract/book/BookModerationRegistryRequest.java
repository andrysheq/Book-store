package com.example.library.model.contract.book;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Фильтры для поиска книг в каталоге модератора")
public record BookModerationRegistryRequest(
        @Schema(
                description = "ID статуса (1=В наличии, 3=Заблокирована)",
                example = "1",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Integer statusId,

        @Schema(
                description = "ID автора для фильтрации",
                example = "1",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Integer authorId,

        @Schema(
                description = "ID жанра для фильтрации",
                example = "4",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Integer genreId,

        @Schema(
                description = "Название или часть названия книги для поиска",
                example = "Война",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String title,

        @Schema(
                description = "Искать только заблокированные книги с указанной причиной",
                example = "true",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Boolean hasBlockingReason
) {
}