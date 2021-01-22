package dk.innotech.user.assertions;

import io.swagger.annotations.Api;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class SwaggerApiAssert extends ClassAssert<SwaggerApiAssert> {
    private final Api annotation;
    private final String annotationName;

    SwaggerApiAssert(Class<?> actual, Api annotation) {
        super(actual, SwaggerApiAssert.class);
        this.annotation = annotation;
        annotationName = annotation.getClass().getCanonicalName();
    }

    public SwaggerApiAssert withValue() {
        return withValue(null);
    }

    public SwaggerApiAssert withValue(String expected) {
        var actual = annotation.value();
        if (expected == null) {
            if (!StringUtils.hasText(actual)) {
                failWithMessage("Expected %s-annotation to have a value", annotationName);
            }
        } else if (!expected.equals(actual)) {
            failWithMessage("Expected %s-annotation to have a value of '%s'", annotationName, expected);
        }
        return this;
    }

    public SwaggerApiAssert withTags(String... tags) {
        if (tags.length > 0) {
            var expected = Arrays.asList(tags);
            if (!Arrays.stream(annotation.tags()).allMatch(expected::contains)) {
                failWithMessage("Expected class to have annotation of type %s with tags: %s",
                        actual.getName(), String.join(", ", tags));
            }
        } else if (annotation.tags().length == 0) {
            failWithMessage("Expected class to have annotation of type %s with tags", annotationName);
        }
        return this;
    }
}


