package com.example.library.dto.request;

import com.example.library.dto.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AuthorRecord", description = "Объект автора")
public class AuthorRecord implements Serializable {

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
    @Enumerated()
    @NotNull
    private Gender gender;

    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    @Past(message = "Author birthdate must be in past")
    @Schema(name = "birthDate", description = "Дата рождения автора", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "dd.MM.yyyy", example = "18.08.2023")
    private LocalDate birthDate;

}
