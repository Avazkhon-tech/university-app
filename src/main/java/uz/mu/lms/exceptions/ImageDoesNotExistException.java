package uz.mu.lms.exceptions;

public class ImageDoesNotExistException extends RuntimeException {
    public ImageDoesNotExistException(String message) {
        super(message);
    }
}
