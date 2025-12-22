package com.example.library.mapper.book;

import com.example.library.model.contract.book.BookDetailView;
import com.example.library.model.contract.book.BookRegistryView;
import com.example.library.model.dao.BookEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookModerationConverter {

    public BookRegistryView toBookRegistryView(BookEntity entity) {
        if (entity == null) {
            return null;
        }

        return new BookRegistryView(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getBookStatus().getTitle(),
                entity.getAuthor().getFirstName() + " " + entity.getAuthor().getLastName(),
                entity.getGenre().getName(),
                entity.getBlockingReason() != null ? entity.getBlockingReason().getTitle() : null
        );
    }

    public BookDetailView toBookDetailView(BookEntity entity) {
        if (entity == null) {
            return null;
        }

        var reviews = entity.getReviews() != null ?
                entity.getReviews().stream()
                        .map(r -> new BookDetailView.ReviewInfo(
                                r.getId(),
                                r.getContent(),
                                r.getRating(),
                                r.getReviewStatus().getTitle(),
                                r.getUserNickname(),
                                r.getAudit().getCreatedAt()
                        ))
                        .collect(Collectors.toList())
                : null;

        return new BookDetailView(
                entity.getId(),
                entity.getTitle(),
                entity.getPrice(),
                entity.getBookStatus(),
                new BookDetailView.AuthorInfo(
                        entity.getAuthor().getId(),
                        entity.getAuthor().getFirstName(),
                        entity.getAuthor().getLastName(),
                        entity.getAuthor().getEmail()
                ),
                entity.getDescription(),
                new BookDetailView.GenreInfo(
                        entity.getGenre().getId(),
                        entity.getGenre().getName()
                ),
                entity.getBlockingReason() != null ? entity.getBlockingReason().getTitle() : null,
                reviews
        );
    }
}
