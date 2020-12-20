package dk.innotech.tracker.user;

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException(String username) {
        super(String.format("Invalid credentials provided (for username '%s')", username));
    }
}
