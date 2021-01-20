package dk.innotech.user.models;

import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class ErrorResponse {
    private final static String TIMESTAMP_KEY = "timestamp";
    private final static String STATUS_KEY = "status";
    private final static String ERRORS_KEY = "errors";

    @Getter
    @Setter
    private Date timestamp;

    @Getter
    @Setter
    private int status;

    @Getter
    @Setter
    private List<String> errors;

    public ErrorResponse() {
        timestamp = new Date();
        status = 500;
        errors = new ArrayList<>();
    }

    public void addError(Object error) {
        String asString = null;
        if (error instanceof String) {
            asString = (String)error;
        } else if (error instanceof AuthenticationException) {
            var authException = (AuthenticationException)error;
            if (authException.getCause() != null) {
                asString = authException.getCause().getMessage() + " - " + authException.getMessage();
            } else {
                asString = authException.getMessage();
            }
        } else if (error instanceof MethodArgumentNotValidException) {
            var validationException = (MethodArgumentNotValidException)error;
            errors.addAll(validationException.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(toList()));
        } else if (error instanceof Throwable) {
            asString = ((Throwable) error).getMessage();
        }

        if (asString != null) {
            errors.add(asString);
        }
    }

    public Map<String, Object> asMap() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, timestamp);
        body.put(STATUS_KEY, status);
        body.put(ERRORS_KEY, errors);
        return body;
    }

    public ResponseEntity<Object> asResponseEntity(HttpHeaders headers, HttpStatus status) {
        this.status = status.value();
        return new ResponseEntity<>(asMap(), headers, status);
    }

    public static ErrorResponse fromError(Object error) {
        var response = new ErrorResponse();
        response.addError(error);
        return response;
    }
}
