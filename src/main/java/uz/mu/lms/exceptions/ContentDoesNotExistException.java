package uz.mu.lms.exceptions;


public class ContentDoesNotExistException extends RuntimeException {
    public ContentDoesNotExistException(String message) {
        super(message);
    }
}