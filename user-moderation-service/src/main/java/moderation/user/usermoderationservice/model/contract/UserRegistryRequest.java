package moderation.user.usermoderationservice.model.contract;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Фильтры для поиска пользователей")
public record UserRegistryRequest(
        @Schema(
                description = "ID статуса (1=Активен, 2=Заблокирован)",
                example = "1",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Integer statusId,

        @Schema(
                description = "Часть email или имени пользователя",
                example = "@mail.ru",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String searchLike
) {
}