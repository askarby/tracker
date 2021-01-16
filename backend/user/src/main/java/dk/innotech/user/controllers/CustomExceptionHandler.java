package dk.innotech.user.controllers;

import dk.innotech.user.models.ErrorResponse;
import dk.innotech.user.services.AlreadyExistsException;
import dk.innotech.user.services.NotExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return ErrorResponse.fromError(ex).asResponseEntity(headers, status);
    }

    @ExceptionHandler({
            AlreadyExistsException.class,
            NotExistsException.class
    })
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        HttpHeaders headers = HttpHeaders.EMPTY;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof AlreadyExistsException) {
            status = HttpStatus.CONFLICT;
        } else if (ex instanceof NotExistsException) {
            status = HttpStatus.NOT_FOUND;
        }
        return ErrorResponse.fromError(ex).asResponseEntity(headers, status);
    }
}
