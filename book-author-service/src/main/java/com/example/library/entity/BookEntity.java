package com.example.library.entity;

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
public class BookEntity extends BaseEntity {

    @Schema(description = "Название книги")
    @NotNull
    private String title;

    @Schema(description = "Количество страниц")
    @NotNull
    private Integer pageAmount;

}
