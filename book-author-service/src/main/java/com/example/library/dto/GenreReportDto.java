package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreReportDto {
    private String genreName;
    private Long bookCount;
    private BigDecimal averageRating;
    private Long reviewCount;
    private Integer minRating;
    private Integer maxRating;
}
