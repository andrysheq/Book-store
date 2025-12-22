package com.example.library.service.spec;

import com.example.library.model.dao.ReviewEntity;
import com.example.library.model.enums.ReviewStatusEnum;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpecificationBuilder {

    private static final String LIKE_STRING_TEMPLATE = "%%%s%%";

    private Specification<ReviewEntity> spec = Specification.where(null);

    public ReviewSpecificationBuilder withStatus(ReviewStatusEnum status) {
        if (status != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("reviewStatus"), status)
            );
        }
        return this;
    }

    public ReviewSpecificationBuilder withSearchLike(@NotNull String search) {
        if (search != null) {
            String likeSearch = LIKE_STRING_TEMPLATE.formatted(search.toLowerCase());
            spec = spec.and((root, query, cb) -> {
                Predicate titlePredicate = cb.like(cb.lower(root.get("book").get("title")), likeSearch);

                Expression<String> authorFullName = cb.concat(
                        cb.concat(root.get("book").get("author").get("firstName"), " "),
                        root.get("book").get("author").get("lastName")
                );
                Predicate authorPredicate = cb.like(cb.lower(authorFullName), likeSearch);

                return cb.or(titlePredicate, authorPredicate);
            });
        }
        return this;
    }

    public Specification<ReviewEntity> build() {
        return spec;
    }
}
