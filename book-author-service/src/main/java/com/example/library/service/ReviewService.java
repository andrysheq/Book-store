package com.example.library.service;

import com.example.library.dto.response.ReviewResponse;
import com.example.library.entity.ReviewEntity;
import com.example.library.entity.ReviewStatusEntity;
import com.example.library.repository.ReviewRepository;
import com.example.library.repository.ReviewStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;


@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewStatusRepository reviewStatusRepository;

    @Transactional
    public ReviewResponse setReviewStatus(Long reviewId, Long reviewStatusId) throws BadRequestException {
        log.info("Установка статуса {} для отзыва {}", reviewStatusId, reviewId);

        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BadRequestException(
                        "Отзыва с id = " + reviewId + " не существует."));

        ReviewStatusEntity reviewStatus = reviewStatusRepository.findById(reviewStatusId)
                .orElseThrow(() -> new BadRequestException(
                        "Статуса с id = " + reviewStatusId + " не существует."));

        review.setReviewStatus(reviewStatus);
        return mapToReviewResponse(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public ReviewResponse getReview(Long reviewId) throws BadRequestException {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BadRequestException("Отзыва с id = " + reviewId + "не существует."));
        return mapToReviewResponse(review);
    }

    public ReviewResponse mapToReviewResponse(ReviewEntity review){
        return new ReviewResponse(review.getId(),
                review.getContent(),
                review.getRating(),
                review.getDate(),
                review.getCreatedDate().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")),
                review.getBook().getTitle(),
                review.getUser().getId().longValue(),
                review.getUser().getFirstName(),
                review.getReviewStatus().getName());
    }

    @Transactional()
    public void deleteReview(Long reviewId){
        reviewRepository.deleteById(reviewId);
    }
}
