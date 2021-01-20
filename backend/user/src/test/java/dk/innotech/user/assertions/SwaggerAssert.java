package dk.innotech.user.assertions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * AssertJ assertions for classes with {@link io.swagger.annotations}-annotations.
 */
public class SwaggerAssert extends ClassAssert<SwaggerAssert> {
    public SwaggerAssert(Class<?> actual) {
        super(actual, SwaggerAssert.class);
    }

    public SwaggerApiAssert hasApiAnnotation() {
        var annotation = getClassAnnotation(Api.class);
        return new SwaggerApiAssert(actual, annotation);
    }

    public SwaggerApiOperationAssert hasApiOperationAnnotation(String methodName) {
        var annotation = getMethodAnnotation(methodName, ApiOperation.class);
        return new SwaggerApiOperationAssert(actual, annotation);
    }
}


