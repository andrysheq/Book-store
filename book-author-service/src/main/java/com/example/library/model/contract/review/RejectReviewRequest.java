package com.example.library.model.contract.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * DTO запроса на отклонение рецензии
 */
@Schema(description = "Запрос на отклонение рецензии модератором")
public record RejectReviewRequest(
        @Schema(
                description = "ID причины отклонения (1=Спам, 2=Оскорбительный контент, 3=Содержание не относится к книге, 4=Рекламный контент, 5=Ненормативная лексика, 6=Дублирование, 7=Другое)",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "1"
        )
        @NotNull(message = "Причина отклонения обязательна")
        Integer reasonId,

        @Schema(
                description = "Комментарий модератора к отклонению рецензии",
                example = "Множество восклицательных знаков, характерный спам"
        )
        String comment
) {
}
