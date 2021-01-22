package dk.innotech.user.assertions;

import io.swagger.annotations.*;
import org.assertj.core.api.AbstractAssert;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.*;

/**
 * AssertJ assertions for classes with {@link io.swagger.annotations}-annotations.
 */
public class SwaggerAssert extends AbstractAssert<SwaggerAssert, Class<?>> {
    private AnnotationSelector selector;

    public SwaggerAssert(Class<?> actual) {
        super(actual, SwaggerAssert.class);
        selector = AnnotationSelector.of(actual);
    }

    public SwaggerApiAssert hasApiAnnotation() {
        var annotation = selector.getClassAnnotation(Api.class);
        return new SwaggerApiAssert(actual, annotation);
    }

    public SwaggerApiOperationAssert hasApiOperationAnnotation(String methodName) {
        var annotation = selector.getMethodAnnotation(methodName, ApiOperation.class);
        return new SwaggerApiOperationAssert(actual, annotation);
    }

    public SwaggerApiResponseAssert hasApiResponseAnnotation(String methodName) {
        var annotations = selector.getMethodAnnotations(methodName, ApiResponse.class, ApiResponses.class)
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

    public SwaggerApiModelAssert hasApiModel() {
        var annotation = selector.getClassAnnotation(ApiModel.class);
        return new SwaggerApiModelAssert(actual, annotation);
    }

    public SwaggerApiModelPropertyAssert hasApiModelField(String fieldName) {
        var annotation = selector.getFieldAnnotation(fieldName, ApiModelProperty.class);
        return new SwaggerApiModelPropertyAssert(actual, annotation);
    }
}


