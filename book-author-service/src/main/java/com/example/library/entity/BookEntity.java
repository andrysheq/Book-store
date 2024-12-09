package com.example.library.entity;

import com.example.library.dto.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Schema(name = "Книга")
@Entity
@Table(name = "book")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class BookEntity extends BaseEntity {

    @Schema(description = "Название книги")
    @NotNull
    private String title;

    @Schema(description = "Количество страниц")
    @NotNull
    private Integer pageAmount;

    @Schema(description = "ID пользователя, подтверждающего добавление объекта")
    @Nullable
    private Long userId;

    @Schema(description = "Статус объекта")
    @Nullable
    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.NOT_CONFIRMED;

}
