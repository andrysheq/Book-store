package com.example.library.model.contract.review;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReviewRegistryRequest(
        @Schema(
                description = "ID статуса 1-На рассмотрении,2-Подтвержден, 3-Отклонен",
                example = "1",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Integer statusId,

        @Schema(
                description = "Часть названия книги или имени, фамилии автора для поиска отзыва",
                example = "Война",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String searchLike
) {
}
