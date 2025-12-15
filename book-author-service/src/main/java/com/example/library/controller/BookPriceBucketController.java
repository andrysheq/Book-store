package com.example.library.controller;

import com.example.library.dto.BookWithBucketRegistryDto;
import com.example.library.service.BookPriceBucketReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports/book-price-bucket")
@RequiredArgsConstructor
public class BookPriceBucketController {

    private final BookPriceBucketReportService service;

    /**
     * Получить отчет по ценовым диапазонам
     */
    @GetMapping
    public List<BookWithBucketRegistryDto> getBucketReport() {
        return service.getBookPriceBucketReport();
    }
}

