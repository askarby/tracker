package dk.innotech.user.assertions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.*;

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

    public SwaggerApiResponseAssert hasApiResponseAnnotation(String methodName) {
        var annotations = getMethodAnnotations(methodName, ApiResponse.class, ApiResponses.class)
                .stream().flatMap(each -> {
                    if (each instanceof ApiResponse) {
                        return of(each);
                    } else if (each instanceof ApiResponses) {
                        return of(((ApiResponses) each).value());
                    } else {
                        return Stream.empty();
                    }
                })
                .map(ApiResponse.class::cast)
                .collect(toList());
        return new SwaggerApiResponseAssert(actual, annotations);
    }
}


