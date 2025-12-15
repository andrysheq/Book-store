package com.example.library.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReviewResponse(
        Integer id,
        String content,
        Short rating,
        LocalDate date,
        String createdDate,
        String bookTitle,
        Long userId,
        String userName,
        String reviewStatusName
) {
}

