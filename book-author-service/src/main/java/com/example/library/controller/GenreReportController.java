package com.example.library.controller;

import com.example.library.dto.GenreReportDto;
import com.example.library.service.GenreReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports/genres")
@RequiredArgsConstructor
public class GenreReportController {

    private final GenreReportService service;

    /**
     * Получить полный отчет по жанрам
     */
    @GetMapping
    public List<GenreReportDto> getGenreReport() {
        return service.getGenreReport();
    }

}

