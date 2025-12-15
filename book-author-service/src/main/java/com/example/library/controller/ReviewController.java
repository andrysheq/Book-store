// ReviewController.java - исправленный контроллер
package com.example.library.controller;

import com.example.library.dto.response.ErrorResponse;
import com.example.library.dto.response.ReviewResponse;
import com.example.library.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * GET /reviews/{reviewId}
     * Получить отзыв по ID
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long reviewId) {
        log.info("Получение отзыва с ID: {}", reviewId);
        return ResponseEntity.ok(reviewService.getReview(reviewId));
    }

    /**
     * PUT /reviews/{reviewId}/status/{reviewStatusId}
     * Установить статус отзыва
     */
    @PutMapping("/{reviewId}/status/{reviewStatusId}")
    public ResponseEntity<ReviewResponse> setReviewStatus(
            @PathVariable Long reviewId,
            @PathVariable Long reviewStatusId) {
        log.info("Установка статуса {} для отзыва {}", reviewStatusId, reviewId);
        return ResponseEntity.ok(reviewService.setReviewStatus(reviewId, reviewStatusId));
    }

    /**
     * DELETE /reviews/{reviewId}
     * Удалить отзыв
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        log.info("Удаление отзыва с ID: {}", reviewId);
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Обработчик для RuntimeException
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException: {}", e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Обработчик для всех остальных исключений
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
