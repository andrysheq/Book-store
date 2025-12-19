package com.example.library.service.spec;

import com.example.library.model.dao.BookEntity;
import com.example.library.model.enums.BookStatusEnum;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specification Builder для фильтрации книг при модерации
 */
public class BookModerationSpecificationBuilder {

    private Specification<BookEntity> spec = Specification.where(null);

    public BookModerationSpecificationBuilder withStatus(BookStatusEnum status) {
        if (status != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("bookStatus"), status)
            );
        }
        return this;
    }

    public BookModerationSpecificationBuilder withAuthorId(Integer authorId) {
        if (authorId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("author").get("id"), authorId)
            );
        }
        return this;
    }

    public BookModerationSpecificationBuilder withGenreId(Integer genreId) {
        if (genreId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("genre").get("id"), genreId)
            );
        }
        return this;
    }

    public BookModerationSpecificationBuilder withTitleLike(String title) {
        if (title != null && !title.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%")
            );
        }
        return this;
    }

    public Specification<BookEntity> build() {
        return spec;
    }
}
