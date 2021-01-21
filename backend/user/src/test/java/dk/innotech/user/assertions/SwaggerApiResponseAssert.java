package dk.innotech.user.assertions;

import io.swagger.annotations.ApiResponse;

import java.util.List;

public class SwaggerApiResponseAssert extends ClassAssert<SwaggerApiResponseAssert> {
    private final List<ApiResponse> annotations;
    private final String annotationName;

    SwaggerApiResponseAssert(Class<?> actual, List<ApiResponse> annotations) {
        super(actual, SwaggerApiResponseAssert.class);
        this.annotations = annotations;
        annotationName = ApiResponse.class.getName();
    }

    public SwaggerApiResponseAssert withCode(int code) {
        var found = annotations.stream()
                .filter(candidate -> candidate.code() == code)
                .findFirst();

        if (found.isEmpty()) {
            failWithMessage("Expected a %s-annotation with %d code", annotationName, code);
        }

        return this;
    }

    public SwaggerApiResponseAssert withCodeAndMessage(int code, String message) {
        var found = annotations.stream()
                .filter(candidate -> {
                    var codeMatch = candidate.code() == code;
                    var messageMatch = candidate.message().equals(message);
                    return codeMatch && messageMatch;
                })
                .findFirst();

        if (found.isEmpty()) {
            failWithMessage("Expected a %s-annotation with %d code, having message '%s'",
                    annotationName, code, message);
        }

        return this;
    }
}


