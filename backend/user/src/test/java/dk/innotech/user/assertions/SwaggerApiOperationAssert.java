package dk.innotech.user.assertions;

import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class SwaggerApiOperationAssert extends ClassAssert<SwaggerApiOperationAssert> {
    private final ApiOperation annotation;
    private final String annotationName;

    SwaggerApiOperationAssert(Class<?> actual, ApiOperation annotation) {
        super(actual, SwaggerApiOperationAssert.class);
        this.annotation = annotation;
        annotationName = annotation.getClass().getCanonicalName();
    }

    public SwaggerApiOperationAssert withValue() {
        return withValue(null);
    }

    public SwaggerApiOperationAssert withValue(String expected) {
        hasStringAnnotationProperty(annotation, "value", annotation::value, expected);
        return this;
    }

    public SwaggerApiOperationAssert withNotes() {
        return withNotes(null);
    }

    public SwaggerApiOperationAssert withNotes(String expected) {
        hasStringAnnotationProperty(annotation, "notes", annotation::notes, expected);
        return this;
    }

    public SwaggerApiOperationAssert withTags(String... tags) {
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


