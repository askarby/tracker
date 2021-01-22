package dk.innotech.user.assertions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class SwaggerApiModelAssert extends ClassAssert<SwaggerApiModelAssert> {
    private final ApiModel annotation;
    private final String annotationName;

    SwaggerApiModelAssert(Class<?> actual, ApiModel annotation) {
        super(actual, SwaggerApiModelAssert.class);
        this.annotation = annotation;
        annotationName = annotation.getClass().getCanonicalName();
    }

    public SwaggerApiModelAssert withValue() {
        return withValue(null);
    }

    public SwaggerApiModelAssert withValue(String expected) {
        hasStringAnnotationProperty(annotation, "value", annotation::value, expected);
        return this;
    }

    public SwaggerApiModelAssert withDescription() {
        return withDescription(null);
    }

    public SwaggerApiModelAssert withDescription(String expected) {
        hasStringAnnotationProperty(annotation, "description", annotation::description, expected);
        return this;
    }
}


