package com.example.library.service;

import com.example.library.dto.response.ReviewResponse;
import com.example.library.entity.ReviewEntity;
import com.example.library.entity.ReviewStatusEntity;
import com.example.library.exception.CustomBadRequestException;
import com.example.library.repository.ReviewRepository;
import com.example.library.repository.ReviewStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewStatusRepository reviewStatusRepository;

    /**
     * Преобразовать ReviewEntity в ReviewResponse
     */
    private ReviewResponse mapToReviewResponse(ReviewEntity review) {
        return new ReviewResponse(
                review.getId(),
                review.getContent(),
                review.getRating(),
                review.getDate(),
                review.getCreatedDate().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")),
                review.getBook().getTitle(),
                review.getUser().getId().longValue(),
                review.getUser().getFirstName(),
                review.getReviewStatus().getName()
        );
    }

    /**
     * Получить отзыв по ID
     */
    @Transactional(readOnly = true)
    public ReviewResponse getReview(Long reviewId) {
        log.info("Получение отзыва с ID: {}", reviewId);

        return reviewRepository.findById(reviewId)
                .map(this::mapToReviewResponse)
                .orElseThrow(() -> new CustomBadRequestException(
                        "Отзыва с id = " + reviewId + " не существует."));
    }

    /**
     * Установить статус отзыва
     */
    @Transactional
    public ReviewResponse setReviewStatus(Long reviewId, Long reviewStatusId) {
        log.info("Установка статуса {} для отзыва {}", reviewStatusId, reviewId);

        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomBadRequestException(
                        "Отзыва с id = " + reviewId + " не существует."));

        ReviewStatusEntity reviewStatus = reviewStatusRepository.findById(reviewStatusId)
                .orElseThrow(() -> new CustomBadRequestException(
                        "Статуса с id = " + reviewStatusId + " не существует."));

        review.setReviewStatus(reviewStatus);
        reviewRepository.save(review);

        return mapToReviewResponse(review);
    }

    /**
     * Удалить отзыв
     */
    @Transactional
    public void deleteReview(Long reviewId) {
        log.info("Удаление отзыва с ID: {}", reviewId);

        if (!reviewRepository.existsById(reviewId)) {
            throw new CustomBadRequestException(
                    "Отзыва с id = " + reviewId + " не существует.");
        }

        reviewRepository.deleteById(reviewId);
    }
}
