package dk.innotech.user.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnmodifiableException extends RuntimeException {
    public UnmodifiableException(String message) {
        super(message);
    }
}
