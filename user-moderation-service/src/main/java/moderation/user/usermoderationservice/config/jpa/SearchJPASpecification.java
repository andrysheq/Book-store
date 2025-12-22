package moderation.user.usermoderationservice.config.jpa;

import com.example.library.config.jpa.SearchCriteria;
import com.example.library.config.jpa.SpecificationBuildException;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@SuppressWarnings({"rawtypes", "unchecked"})
public class SearchJPASpecification<T> implements Specification<T> {
    private static final String ERROR_GREATER_THEN_CRITERIA = "Can't create greater than %s criteria";
    private static final String ERROR_LESS_THEN_CRITERIA = "Can't create less than %s criteria";
    private static final String ERROR_NOT_EQUAL_CRITERIA = "Can't create not equal %s criteria";
    private static final String ERROR_EQUAL_CRITERIA = "Can't create equal %s criteria";

    private final transient SearchCriteria criteria;

    public SearchJPASpecification(final SearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Expression expression = buildExpression(root);
        if (expression == null) {
            return null;
        }
        List<Comparable> parsedValues = getParsedValues(expression);
        if (criteria.equals(">")) {
            return getHigherPredicate(builder, expression, parsedValues);
        }
        if (criteria.equals(">-")) {
            return getHigherOrEqual(builder, expression, parsedValues);
        }
        if (criteria.equals("?>")) {
            return getHigherOrEqualOrNullPredicate(builder, expression, parsedValues);
        }
        if (criteria.equals("<")) {
            return getLowerPredicate(builder, expression, parsedValues);
        }
        if (criteria.equals("<-")) {
            return getLowerOrEqualPredicate(builder, expression, parsedValues);
        }
        if (criteria.equals("?<")) {
            return getLowerOrEqualOrNullPredicate(builder, expression, parsedValues);
        }
        if (criteria.equals("!")) {
            return getNotEqualsPredicate(builder, parsedValues, expression);
        }
        if (criteria.equals(">>")) {
            return getEqualsCollectionPredicate(expression, parsedValues);
        }
        if (criteria.equals(":") || criteria.getOperation().equalsIgnoreCase("::")) {
            return getEqualsPredicate(builder, parsedValues, expression);
        }
        return null;
    }

    private List<Comparable> getParsedValues(Expression expression) {
        if (!criteria.getOperation().equalsIgnoreCase(">>")) return List.of(tryParseValue(expression, criteria.getValue()));
        return Arrays.stream(criteria.getValue().toString().split(","))
                .map(value -> tryParseValue(expression, value))
                .toList();
    }

    private Predicate getHigherPredicate(CriteriaBuilder builder, Expression expression, List<Comparable> parsedValues) {
        try {
            return builder.greaterThan(expression, parsedValues.getFirst());
        } catch (Exception e) {
            throw new SpecificationBuildException(String.format(ERROR_GREATER_THEN_CRITERIA, criteria.getKey()), e);
        }
    }

    private Predicate getHigherOrEqual(CriteriaBuilder builder, Expression expression, List<Comparable> parsedValues) {
        try {
            return builder.greaterThanOrEqualTo(expression, parsedValues.getFirst());
        } catch (Exception e) {
            throw new SpecificationBuildException(String.format(ERROR_GREATER_THEN_CRITERIA,
                    criteria.getKey()), e);
        }
    }

    private Predicate getHigherOrEqualOrNullPredicate(CriteriaBuilder builder, Expression expression, List<Comparable> parsedValues) {
        try {
            return builder.or(builder.greaterThanOrEqualTo(expression, parsedValues.getFirst()),
                    builder.isNull(expression));
        } catch (Exception e) {
            throw new SpecificationBuildException(String.format(ERROR_GREATER_THEN_CRITERIA, criteria.getKey()), e);
        }
    }

    private Predicate getLowerPredicate(CriteriaBuilder builder, Expression expression, List<Comparable> parsedValues) {
        try {
            return builder.lessThan(expression, parsedValues.getFirst());
        } catch (Exception e) {
            throw new SpecificationBuildException(String.format(ERROR_LESS_THEN_CRITERIA, criteria.getKey()), e);
        }
    }

    private Predicate getLowerOrEqualPredicate(CriteriaBuilder builder, Expression expression, List<Comparable> parsedValues) {
        try {
            return builder.lessThanOrEqualTo(expression, parsedValues.getFirst());
        } catch (Exception e) {
            throw new SpecificationBuildException(String.format(ERROR_LESS_THEN_CRITERIA, criteria.getKey()), e);
        }
    }

    private Predicate getLowerOrEqualOrNullPredicate(CriteriaBuilder builder, Expression expression, List<Comparable> parsedValues) {
        try {
            return builder.or(builder.lessThanOrEqualTo(expression, parsedValues.getFirst()), builder.isNull(expression));
        } catch (Exception e) {
            throw new SpecificationBuildException(String.format(ERROR_LESS_THEN_CRITERIA, criteria.getKey()), e);
        }
    }

    private Predicate getNotEqualsPredicate(CriteriaBuilder builder, List<Comparable> parsedValues, Expression expression) {
        Comparable parsedValue = parsedValues.getFirst();
        if (parsedValue == null) return builder.isNotNull(expression);
        try {
            return builder.notEqual(expression, parsedValue);
        } catch (Exception e) {
            throw new SpecificationBuildException(String.format(ERROR_NOT_EQUAL_CRITERIA, criteria.getKey()), e);
        }
    }

    private Predicate getEqualsPredicate(CriteriaBuilder builder, List<Comparable> parsedValues, Expression expression) {
        Comparable parsedValue = parsedValues.getFirst();
        if (parsedValue == null) return builder.isNull(expression);
        if (expression.getJavaType() == String.class) {
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                String v = "%" + parsedValue.toString().toLowerCase() + "%";
                return builder.like(builder.lower(expression), v);
            }
            return builder.equal(expression, parsedValue);
        }
        try {
            return builder.equal(expression, parsedValue);
        } catch (Exception e) {
            throw new SpecificationBuildException(String.format(ERROR_EQUAL_CRITERIA, criteria.getKey()), e);
        }
    }

    private Predicate getEqualsCollectionPredicate(Expression expression, List<Comparable> parsedValues) {
        try {
            return expression.in(parsedValues.toArray());
        } catch (Exception e) {
            throw new SpecificationBuildException(String.format(ERROR_NOT_EQUAL_CRITERIA, criteria.getKey()), e);
        }
    }

    private Expression buildExpression(Root<T> root) {
        String joinSymbol = "__";
        if (!criteria.getKey().contains(joinSymbol)) {
            return tryGet(root, criteria.getKey());
        }
        // join
        String[] keys = criteria.getKey().split(joinSymbol);
        Join join = null;
        for (int i = 0; i < keys.length - 1; i++) { // for ignore last item
            String key = keys[i];
            if (join == null) {
                join = tryJoin(root, key);
                continue;
            }
            join = tryJoin(join, key);
        }
        if (join == null)
            return null;
        String key = keys[keys.length - 1]; // last item
        return tryGet(join, key);
    }

    private Expression tryGet(From from, String key) {
        try {
            return from.get(key);
        } catch (Exception e) {
            throw new SpecificationBuildException("Can't get criteria key", e);
        }
    }

    private Join tryJoin(From<?,?> from, String keyJoin) {
        try {
            for (Join join : from.getJoins()) {
                boolean sameName = join.getAttribute().getName().equals(keyJoin);
                if (sameName && join.getJoinType().equals(JoinType.LEFT)) {
                    return join;
                }
            }
            return from.join(keyJoin, JoinType.LEFT);
        } catch (Exception e) {
            throw new SpecificationBuildException("Can't join criteria key", e);
        }
    }

    private Comparable tryParseValue(Expression expression, Object value) {
        try {
            return parseValue(expression, value);
        } catch (Exception e) {
            throw new SpecificationBuildException(String.format("Can't cast %s to %s type", criteria.getValue(), expression.getJavaType()));
        }
    }

    private Comparable parseValue(Expression expression, Object value) {
        Class aClass = expression.getJavaType();
        if (value.toString().equalsIgnoreCase("null")) return null;
        if (aClass.equals(UUID.class)) return UUID.fromString(value.toString());
        if (aClass.equals(LocalDate.class)) return LocalDate.parse(value.toString(), DateTimeFormatter.ISO_LOCAL_DATE);
        if (aClass.equals(LocalTime.class)) return LocalTime.parse(value.toString(), DateTimeFormatter.ISO_LOCAL_TIME);
        if (aClass.equals(LocalDateTime.class)) return LocalDateTime.parse(value.toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        if (aClass.equals(OffsetDateTime.class)) return OffsetDateTime.parse(value.toString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        if (aClass.equals(BigDecimal.class)) return BigDecimal.valueOf(Double.parseDouble(value.toString()));
        if (aClass.isEnum()) return Enum.valueOf(aClass, value.toString());
        if (aClass.equals(Boolean.class) || aClass.equals(boolean.class)) return Boolean.parseBoolean(value.toString());
        if (aClass.equals(Integer.class) || aClass.equals(int.class)) return Integer.parseInt(value.toString());
        if (aClass.equals(Long.class) || aClass.equals(long.class)) return Long.parseLong(value.toString());
        if (aClass.equals(Double.class) || aClass.equals(double.class)) return Double.parseDouble(value.toString());
        if (aClass.equals(Date.class)) {
            Date date = tryParseDate(value.toString());
            return date != null ? date : new Date(Long.parseLong(value.toString()));
        }
        return value.toString();
    }

    public static Date tryParseDate(@NonNull String value) {
        SimpleDateFormat[] formats = {
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX"),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z"),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z"),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                new SimpleDateFormat("HH:mm dd.MM.yyyy"),
                new SimpleDateFormat("yyyy MM dd"),
                new SimpleDateFormat("yyyyMMdd"),
                new SimpleDateFormat("yyyy-MM-dd"),
                new SimpleDateFormat("dd.MM.yyyy")
        };
        for (SimpleDateFormat format : formats) {
            try {
                return format.parse(value);
            } catch (Exception ignored) {}
        }
        return null;
    }
}
