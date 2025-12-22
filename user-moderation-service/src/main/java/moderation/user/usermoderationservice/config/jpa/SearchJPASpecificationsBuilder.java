package moderation.user.usermoderationservice.config.jpa;

import org.springframework.data.jpa.domain.Specification;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public class SearchJPASpecificationsBuilder<T> {

    private final List<SearchCriteria> params;
    private final Specification<T> specification;

    public SearchJPASpecificationsBuilder(Specification<T> specification) {
        this.params = new LinkedList<>();
        this.specification = specification;
    }

    public SearchJPASpecificationsBuilder() {
        this.params = new LinkedList<>();
        this.specification = null;
    }

    public SearchJPASpecificationsBuilder with(String key, String operation, Object value, String logic) {
        params.add(new SearchCriteria(key, operation, value, logic));
        return this;
    }

    public Specification<T> build() {
        if (params.isEmpty()) {
            return specification;
        }
        List<Specification<T>> specs = new LinkedList<>();
        for (SearchCriteria param : params) {
            specs.add(new SearchJPASpecification<>(param));
        }
        Specification<T> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            SearchCriteria previous = ((SearchJPASpecification) specs.get(i - 1)).getCriteria();
            if (previous.getLogic().equalsIgnoreCase("^"))
                result = Objects.requireNonNull(Specification.where(result)).and(specs.get(i));
            else if (previous.getLogic().equalsIgnoreCase("||"))
                result = Objects.requireNonNull(Specification.where(result)).or(specs.get(i));
        }
        if (this.specification == null) return result;
        return Specification.where(result).and(specification);
    }
}
