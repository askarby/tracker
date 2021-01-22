package dk.innotech.user.assertions;

import io.swagger.annotations.ApiModelProperty;

public class SwaggerApiModelPropertyAssert extends ClassAssert<SwaggerApiModelPropertyAssert> {
    private final ApiModelProperty annotation;
    private final String annotationName;

    SwaggerApiModelPropertyAssert(Class<?> actual, ApiModelProperty annotation) {
        super(actual, SwaggerApiModelPropertyAssert.class);
        this.annotation = annotation;
        annotationName = annotation.getClass().getCanonicalName();
    }

    public SwaggerApiModelPropertyAssert withValue() {
        return withValue(null);
    }

    public SwaggerApiModelPropertyAssert withValue(String expected) {
        hasStringAnnotationProperty(annotation, "value", annotation::value, expected);
        return this;
    }

    public SwaggerApiModelPropertyAssert withNotes() {
        return withNotes(null);
    }

    public SwaggerApiModelPropertyAssert withNotes(String expected) {
        hasStringAnnotationProperty(annotation, "notes", annotation::notes, expected);
        return this;
    }

    public SwaggerApiModelPropertyAssert withExample() {
        return withExample(null);
    }

    public SwaggerApiModelPropertyAssert withExample(String expected) {
        hasStringAnnotationProperty(annotation, "example", annotation::example, expected);
        return this;
    }

    public SwaggerApiModelPropertyAssert thatIsRequired() {
        if (!annotation.required()) {
            failWithMessage("Expected %s-annotation to be required", annotationName);
        }
        return this;
    }

    public SwaggerApiModelPropertyAssert thatIsOptional() {
        if (annotation.required()) {
            failWithMessage("Expected %s-annotation to be optional", annotationName);
        }
        return this;
    }
}


