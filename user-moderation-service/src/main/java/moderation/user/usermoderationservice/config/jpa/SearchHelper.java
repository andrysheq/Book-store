package moderation.user.usermoderationservice.config.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * format {field entity}__{relation}__{field relation}{: or >(-) or <(-) or ! or >>}{value}{|| or ^}...
 * :    equal (for string - like ignore case)
 * ::   equal (for string - equal)
 * >    is greater
 * <    is less
 * >-   is greater or equal
 * <-   is less or equal
 * ?>   is greater or equal OR null
 * ?<   is less or equal OR null
 * !    is not equal
 * >>   is in
 * ^    is logical AND operation
 * ||   is logical OR operation
 * {value}:
 * numbers (123)
 * decimal (123.1)
 * string (val)
 * email (a@a.com)
 * UUID (123e4567-e89b-12d3-a456-556642440000)
 * in collection (val1,val2)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchHelper {
    private static final Pattern pattern = Pattern.compile(
            "([\\w_]+)(:{1,2}|<[-]?|[?]<|[?]>{1,2}|>{1,2}[-]?|[-]?>{1,2}|!)([^\\\\|^]+)([\\\\|]{2}|[\\\\^])?",
            Pattern.UNICODE_CHARACTER_CLASS);

    public static <T> Specification<T> createJPASpecification(String search) throws SpecificationBuildException {
        return createJPASpecification(null, search);
    }

    public static <T> Specification<T> createJPASpecification(Specification<T> specification, String search)
            throws SpecificationBuildException {
        search = clearString(search);
        ////
        SearchJPASpecificationsBuilder<T> builder = new SearchJPASpecificationsBuilder<>(specification);
        if (!ObjectUtils.isEmpty(search))
            prepareBuilder(builder, search);
        return builder.build();
    }

    private static void prepareBuilder(SearchJPASpecificationsBuilder builder, String search) {
        Matcher matcher = pattern.matcher(search);
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
        }
    }

    private static String clearString(String val) {
        return val.replaceAll("[~#$%&*()=/;'{}]", "");
    }

    public static Pattern getPattern() {
        return pattern;
    }
}
