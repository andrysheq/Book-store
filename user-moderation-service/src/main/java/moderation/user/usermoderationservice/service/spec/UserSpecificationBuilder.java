package moderation.user.usermoderationservice.service.spec;

import jakarta.persistence.criteria.Predicate;
import jakarta.validation.constraints.NotNull;
import moderation.user.usermoderationservice.model.dao.UserEntity;
import moderation.user.usermoderationservice.model.enums.UserStatusEnum;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecificationBuilder {

    private static final String LIKE_STRING_TEMPLATE = "%%%s%%";

    private Specification<UserEntity> spec = Specification.where(null);

    public UserSpecificationBuilder withStatus(UserStatusEnum status) {
        if (status != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), status)
            );
        }
        return this;
    }

    public UserSpecificationBuilder withSearchLike(@NotNull String search) {
        if (search != null) {
            String likeSearch = LIKE_STRING_TEMPLATE.formatted(search.toLowerCase());
            spec = spec.and((root, query, cb) -> {
                Predicate namePredicate = cb.like(cb.lower(root.get("firstName")), likeSearch);

                Predicate emailPredicate = cb.like(cb.lower(root.get("email")), likeSearch);

                return cb.or(namePredicate, emailPredicate);
            });
        }
        return this;
    }

    public Specification<UserEntity> build() {
        return spec;
    }
}
