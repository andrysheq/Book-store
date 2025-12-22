package moderation.user.usermoderationservice.model.contract;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UserView(@Schema(description = "Идентификатор пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
                       Long id,

                       @Schema(description = "Название книги", requiredMode = Schema.RequiredMode.REQUIRED)
                       String email,

                       @Schema(description = "Описание", requiredMode = Schema.RequiredMode.REQUIRED)
                       String firstName,

                       @Schema(description = "Название книги", requiredMode = Schema.RequiredMode.REQUIRED)
                       String userStatus,

                       @Schema(description = "Название книги", requiredMode = Schema.RequiredMode.REQUIRED)
                       LocalDateTime createdAt,

                       @Schema(description = "Название книги", requiredMode = Schema.RequiredMode.REQUIRED)
                       LocalDateTime statusUpdatedAt
                       ) {}
