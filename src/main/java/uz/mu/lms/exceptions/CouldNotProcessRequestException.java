package uz.mu.lms.exceptions;


public class CouldNotProcessRequestException extends RuntimeException {
    public CouldNotProcessRequestException(String message) {
        super(message);
    }
}