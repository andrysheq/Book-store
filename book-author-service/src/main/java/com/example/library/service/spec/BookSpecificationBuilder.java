package com.example.library.service.spec;

import com.example.library.model.dao.BookEntity;
import com.example.library.model.enums.BookStatusEnum;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


public class BookSpecificationBuilder {

    private static final String LIKE_STRING_TEMPLATE = "%%%s%%";

    private Specification<BookEntity> spec = Specification.where(null);

    public BookSpecificationBuilder withStatus(BookStatusEnum status) {
        if (status != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("bookStatus"), status)
            );
        }
        return this;
    }

    public BookSpecificationBuilder withSearchLike(String search) {
        if (search != null) {
            String likeSearch = LIKE_STRING_TEMPLATE.formatted(search.toLowerCase());
            spec = spec.and((root, query, cb) -> {
                Predicate titlePredicate = cb.like(cb.lower(root.get("title")), likeSearch);

                Expression<String> authorFullName = cb.concat(
                        cb.concat(root.get("author").get("firstName"), " "),
                        root.get("author").get("lastName")
                );
                Predicate authorPredicate = cb.like(cb.lower(authorFullName), likeSearch);

                return cb.or(titlePredicate, authorPredicate);
            });
        }
        return this;
    }

    public BookSpecificationBuilder withGenreId(Integer genreId) {
        if (genreId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("genre").get("id"), genreId)
            );
        }
        return this;
    }

    public Specification<BookEntity> build() {
        return spec;
    }
}
