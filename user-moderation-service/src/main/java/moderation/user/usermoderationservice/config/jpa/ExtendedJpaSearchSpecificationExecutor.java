package moderation.user.usermoderationservice.config.jpa;

import com.example.library.config.jpa.JpaSearchSpecificationExecutor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface ExtendedJpaSearchSpecificationExecutor<T> extends JpaSearchSpecificationExecutor<T> {
    default Optional<T> findFirst(String search) {
        return findFirst(SearchHelper.createJPASpecification(search));
    }

    default Optional<T> findFirst(Specification<T> spec, String search) {
        return findFirst(SearchHelper.createJPASpecification(spec, search));
    }

    Optional<T> findFirst(Specification<T> spec);
}
