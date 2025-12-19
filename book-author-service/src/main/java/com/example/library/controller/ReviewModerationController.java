package com.example.library.controller;

import com.example.library.handler.ReviewModerationHandler;
import com.example.library.model.contract.review.RejectReviewRequest;
import com.example.library.model.contract.review.ReviewDetailView;
import com.example.library.model.contract.review.ReviewRegistryView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@Validated
@RequiredArgsConstructor
@Tag(name = "ReviewModeration", description = "Модерация рецензий")
public class ReviewModerationController {

    private final ReviewModerationHandler reviewModerationHandler;

    @GetMapping("/pending")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение списка рецензий на рассмотрении")
    public Page<ReviewRegistryView> getPendingReviews(
            @ParameterObject
            Pageable pageable) {
        return reviewModerationHandler.getPendingReviews(pageable);
    }

    @GetMapping("/rejected")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение списка отклоненных рецензий")
    public Page<ReviewRegistryView> getRejectedReviews(
            @ParameterObject
            Pageable pageable) {
        return reviewModerationHandler.getRejectedReviews(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение полной информации о рецензии")
    public ReviewDetailView getReviewDetail(
            @Parameter(description = "Идентификатор рецензии")
            @NotNull
            @PathVariable("id")
            Long reviewId) {
        return reviewModerationHandler.getReviewDetail(reviewId);
    }

    @PutMapping("/{id}/approve")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Подтверждение рецензии")
    public void approveReview(
            @Parameter(description = "Идентификатор рецензии")
            @NotNull
            @PathVariable("id")
            Long reviewId) {
        reviewModerationHandler.approveReview(reviewId);
    }

    @PutMapping("/{id}/reject")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Отклонение рецензии с указанием причины")
    public void rejectReview(
            @Parameter(description = "Идентификатор рецензии")
            @NotNull
            @PathVariable("id")
            Long reviewId,
            @Valid @RequestBody @NotNull
            @Parameter(description = "Запрос на отклонение с причиной")
            RejectReviewRequest request) {
        reviewModerationHandler.rejectReview(reviewId, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление рецензии")
    public void deleteReview(
            @Parameter(description = "Идентификатор рецензии")
            @NotNull
            @PathVariable("id")
            Long reviewId) {
        reviewModerationHandler.deleteReview(reviewId);
    }
}
