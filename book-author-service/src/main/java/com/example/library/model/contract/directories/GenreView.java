package com.example.library.model.contract.directories;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Полная информация о жанре")
public record GenreView(
        @Schema(description = "Идентификатор", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer id,

        @Schema(description = "Наименование")
        String content) {}
