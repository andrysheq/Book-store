package com.example.library.entity;

import com.example.library.dto.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Schema(name = "Автор")
@Entity
@Table(name = "author")
@Getter
@Setter
@RequiredArgsConstructor
public class AuthorEntity extends BaseEntity {

    @Schema(description = "Имя автора")
    @NotNull
    private String firstName;

    @Schema(description = "Фамилия автора")
    @NotNull
    private String lastName;

    @Schema(description = "Отчество автора")
    @Nullable
    private String middleName;

    @Schema(description = "Пол автора")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    @Schema(name = "endDate", description = "Дата рождения автора", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "dd.MM.yyyy", example = "18.08.2023")
    private LocalDate birthDate;
}
