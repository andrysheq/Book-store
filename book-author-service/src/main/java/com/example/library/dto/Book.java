package com.example.library.dto;

import com.example.library.dto.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(name = "Книга", description = "Информация о книге")
public class Book extends BaseDomain {

    @Schema(description = "Название книги")
    @NotNull
    private String title;

    @Schema(description = "Количество страниц")
    @NotNull
    private Integer pageAmount;

    @Schema(description = "ID авторов, работавших над книгой")
    @NotNull
    private Set<Long> authorIds;

    @Schema(description = "ID пользователя, подтверждающего добавление объекта")
    @Nullable
    private Long userId;

    @Schema(description = "Статус объекта")
    @Nullable
    @Enumerated()
    private StatusType status;
}
