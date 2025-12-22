package com.example.library.mapper.book;

import com.example.library.model.contract.review.ReviewDetailView;
import com.example.library.model.contract.review.ReviewRegistryView;
import com.example.library.model.dao.ReviewEntity;
import org.springframework.stereotype.Component;

/**
 * Converter для преобразования ReviewEntity в DTO
 */
@Component
public class ReviewModerationConverter {

    public ReviewRegistryView toReviewRegistryView(ReviewEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ReviewRegistryView(
                entity.getId(),
                entity.getContent(),
                entity.getRating(),
                entity.getReviewStatus().getTitle(),
                entity.getBook().getTitle(),
                entity.getUserEmail(),
                entity.getRejectionReason() != null ? entity.getRejectionReason().getTitle() : null,
                entity.getRejectionComment(),
                entity.getAudit().getCreatedAt(),
                entity.getStatusUpdatedAt()
        );
    }

    public ReviewDetailView toReviewDetailView(ReviewEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ReviewDetailView(
                entity.getId(),
                entity.getContent(),
                entity.getRating(),
                entity.getReviewStatus().getTitle(),
                new ReviewDetailView.BookInfo(
                        entity.getBook().getId(),
                        entity.getBook().getTitle()
                ),
                new ReviewDetailView.AuthorInfo(
                        entity.getUserEmail(),
                        entity.getUserNickname()
                ),
                entity.getRejectionReason() != null ? entity.getRejectionReason().getTitle() : null,
                entity.getRejectionComment(),
                entity.getAudit().getCreatedAt(),
                entity.getStatusUpdatedAt()
        );
    }
}
