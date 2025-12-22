package com.example.library.handler;

import com.example.library.exception.NotFoundException;
import com.example.library.mapper.book.ReviewModerationConverter;
import com.example.library.model.contract.review.RejectReviewRequest;
import com.example.library.model.contract.review.ReviewDetailView;
import com.example.library.model.contract.review.ReviewRegistryRequest;
import com.example.library.model.contract.review.ReviewRegistryView;
import com.example.library.model.dao.ReviewEntity;
import com.example.library.service.ReviewModerationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler для операций модерации рецензий
 * Делегирует бизнес-логику в сервисный слой
 */
@Component
@RequiredArgsConstructor
public class ReviewModerationHandler {

    private final ReviewModerationService reviewModerationService;
    private final ReviewModerationConverter reviewModerationConverter;

    @Transactional(readOnly = true)
    public Page<ReviewRegistryView> getPendingReviews(Pageable pageable) {
        return reviewModerationService.getPendingReviews(pageable)
                .map(reviewModerationConverter::toReviewRegistryView);
    }

    @Transactional(readOnly = true)
    public Page<ReviewRegistryView> getRejectedReviews(Pageable pageable) {
        return reviewModerationService.getRejectedReviews(pageable)
                .map(reviewModerationConverter::toReviewRegistryView);
    }

    @Transactional(readOnly = true)
    public ReviewDetailView getReviewDetail(Long reviewId) {
        ReviewEntity review = reviewModerationService.getById(reviewId)
                .orElseThrow(() -> new NotFoundException("Отзыв с id = " + reviewId + " не найден"));
        return reviewModerationConverter.toReviewDetailView(review);
    }

    @Transactional
    public void approveReview(Long reviewId) {
        ReviewEntity review = reviewModerationService.getById(reviewId)
                .orElseThrow(() -> new NotFoundException("Отзыв с id = " + reviewId + " не найден"));

        reviewModerationService.approveReview(review);
    }

    @Transactional
    public void rejectReview(Long reviewId, RejectReviewRequest request) {
        ReviewEntity review = reviewModerationService.getById(reviewId)
                .orElseThrow(() -> new NotFoundException("Отзыв с id = " + reviewId + " не найден"));

        reviewModerationService.rejectReview(review, request);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        ReviewEntity review = reviewModerationService.getById(reviewId)
                .orElseThrow(() -> new NotFoundException("Отзыв с id = " + reviewId + " не найден"));

        reviewModerationService.deleteReview(review);
    }

    @Transactional(readOnly = true)
    public Page<ReviewRegistryView> filterReviews(ReviewRegistryRequest request, Pageable pageable) {
        return reviewModerationService.getReviewRegistry(request, pageable)
                .map(reviewModerationConverter::toReviewRegistryView);
    }
}
