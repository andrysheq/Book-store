package com.example.library.controller;

import com.example.library.dto.response.ReviewResponse;
import com.example.library.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Получить отзыв по ID
     */
    @GetMapping("/{reviewId}")
    public ReviewResponse getReview(@PathVariable Long reviewId) throws BadRequestException {
        log.info("Получение отзыва с ID: {}", reviewId);
        return reviewService.getReview(reviewId);
    }

    /**
     * Установить статус отзыва
     */
    @PutMapping("/{reviewId}/status/{reviewStatusId}")
    public ReviewResponse setReviewStatus(
            @PathVariable Long reviewId,
            @PathVariable Long reviewStatusId) throws BadRequestException {
        log.info("Установка статуса {} для отзыва {}", reviewStatusId, reviewId);
        return reviewService.setReviewStatus(reviewId, reviewStatusId);
    }

    /**
     * Удалить отзыв
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) throws BadRequestException {
        log.info("Удаление отзыва с ID: {}", reviewId);
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}

