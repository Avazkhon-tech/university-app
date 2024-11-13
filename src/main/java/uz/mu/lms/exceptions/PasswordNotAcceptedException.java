package uz.mu.lms.exceptions;


public class PasswordNotAcceptedException extends RuntimeException {
    public PasswordNotAcceptedException(String message) {
        super(message);
    }
}