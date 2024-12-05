package com.example.library.entity;

import com.example.library.dto.enums.StatusType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {
    @Schema(description = "ID объекта")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
