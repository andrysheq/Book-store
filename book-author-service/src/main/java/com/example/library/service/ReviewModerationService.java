package com.example.library.service;

import com.example.library.exception.BadRequestException;
import com.example.library.model.contract.review.RejectReviewRequest;
import com.example.library.model.dao.ReviewEntity;
import com.example.library.model.enums.ReviewRejectionReasonEnum;
import com.example.library.model.enums.ReviewStatusEnum;
import com.example.library.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Сервис для операций модерации рецензий
 */
@Service
@RequiredArgsConstructor
public class ReviewModerationService {

    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public Optional<ReviewEntity> getById(Long id) {
        return reviewRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<ReviewEntity> getPendingReviews(Pageable pageable) {
        return reviewRepository.findByReviewStatus(ReviewStatusEnum.ON_REVIEW, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ReviewEntity> getRejectedReviews(Pageable pageable) {
        return reviewRepository.findByReviewStatus(ReviewStatusEnum.REJECTED, pageable);
    }

    @Transactional
    public void approveReview(ReviewEntity review) {
        if (review.getReviewStatus() != ReviewStatusEnum.ON_REVIEW) {
            throw new BadRequestException("Некорректный статус отзыва");
        }

        review.setReviewStatus(ReviewStatusEnum.APPROVED);
        review.setStatusUpdatedAt(LocalDateTime.now());
        review.setRejectionReason(null);
        review.setRejectionComment(null);

        reviewRepository.save(review);
    }

    @Transactional
    public void rejectReview(ReviewEntity review, RejectReviewRequest request) {
        if (review.getReviewStatus() != ReviewStatusEnum.ON_REVIEW) {
            throw new BadRequestException("Некорректный статус отзыва");
        }

        validateRejectionReason(request.reasonId());

        review.setReviewStatus(ReviewStatusEnum.REJECTED);
        review.setStatusUpdatedAt(LocalDateTime.now());
        review.setRejectionReason(ReviewRejectionReasonEnum.of(request.reasonId()));
        review.setRejectionComment(request.comment());

        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(ReviewEntity review) {
        reviewRepository.delete(review);
    }

    private void validateRejectionReason(Integer reasonId) {
        if (ReviewRejectionReasonEnum.of(reasonId) == null) {
            throw new BadRequestException("Некорректный id причины отклонения");
        }
    }
}
