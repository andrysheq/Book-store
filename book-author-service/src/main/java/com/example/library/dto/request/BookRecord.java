package com.example.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "BookRecord", description = "Объект книги")
public class BookRecord implements Serializable {
    @Schema(description = "Название книги")
    @NotNull
    private String title;

    @Schema(description = "Количество страниц")
    @NotNull
    private Integer pageAmount;

    @Schema(description = "ID авторов, работавших над книгой")
    @NotNull
    private Set<Long> authorIds;
}
