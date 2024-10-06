package com.example.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public abstract class BaseDomain implements Serializable {

    @NotNull
    @Schema(name = "id", description = "Идентификатор записи", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    public BaseDomain() {
    }
}