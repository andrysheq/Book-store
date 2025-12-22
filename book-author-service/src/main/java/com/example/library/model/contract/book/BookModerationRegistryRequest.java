package com.example.library.model.contract.book;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Фильтры для поиска книг в каталоге модератора")
public record BookModerationRegistryRequest(
        @Schema(
                description = "ID статуса (1=В наличии, 2=Заблокирована)",
                example = "1",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Integer statusId,

        @Schema(
                description = "ID жанра для фильтрации",
                example = "4",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Integer genreId,

        @Schema(
                description = "Часть названия или автора для поиска книги",
                example = "Война",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String searchLike
) {
}