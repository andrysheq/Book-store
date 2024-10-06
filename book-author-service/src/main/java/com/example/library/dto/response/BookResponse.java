package com.example.library.dto.response;

import com.example.library.dto.Author;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class BookResponse implements Serializable {
    @Schema(description = "Название книги")
    @NotNull
    private String title;

    @Schema(description = "Количество страниц")
    @NotNull
    private Integer pageAmount;

    @Schema(description = "Авторы")
    @NotNull
    private Set<Author> authors;
}
