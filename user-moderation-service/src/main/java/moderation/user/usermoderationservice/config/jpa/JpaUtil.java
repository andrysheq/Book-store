package moderation.user.usermoderationservice.config.jpa;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JpaUtil {
    public static <T> List<Order> convertToOrdering(Root<T> root, CriteriaBuilder cb, Sort sort) {
        return sort.get().map(o -> {
            String[] attrs = o.getProperty().split("\\.");
            if (attrs.length == 0) return null;
            Path<T> path = root.get(attrs[0]);
            for (int i = 1; i < attrs.length; i++)
                path = path.get(attrs[i]);
            return o.isAscending() ? cb.asc(path) : cb.desc(path);
        })
                .filter(Objects::nonNull)
                .toList();
    }
}
