package com.example.library.service;

import com.example.library.dto.BookWithBucketRegistryDto;
import com.example.library.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BookPriceBucketReportService {

    private final ReviewRepository reviewRepository;

    /**
     * Преобразовать Object[] в DTO
     */
    private BookWithBucketRegistryDto mapToDto(Object[] row) {
        return BookWithBucketRegistryDto.builder()
                .priceRange((String) row[0])
                .bookCount(((Number) row[1]).longValue())
                .averageRating(row[2] != null ? new BigDecimal(row[2].toString()) : null)
                .reviewCount(((Number) row[3]).longValue())
                .build();
    }

    /**
     * Получить полный отчет по ценовым диапазонам
     */
    public List<BookWithBucketRegistryDto> getBookPriceBucketReport() {
        log.info("Запрос отчета по ценовым диапазонам книг");
        return reviewRepository.findBookPriceBucketReport()
                .stream()
                .map(this::mapToDto)
                .toList();
    }
}

