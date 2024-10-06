package com.example.library.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    @Schema(name = "errorCode", description = "Код ошибки")
    private String errorCode;

    @NotBlank
    @Schema(name = "message", description = "Текст ошибки", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

    @Schema(name = "details", description = "Список деталей ошибки")
    private List<String> details;
}
