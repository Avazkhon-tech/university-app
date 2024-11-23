package uz.mu.lms.exceptions;


public class AuthenticationFailure extends RuntimeException {
    public AuthenticationFailure(String message) {
        super(message);
    }
}