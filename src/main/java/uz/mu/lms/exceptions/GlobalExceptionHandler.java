package uz.mu.lms.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.mu.lms.dto.ResponseDto;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationFailure.class)
    public ResponseEntity<ResponseDto<?>> handleAuthenticationFailure(AuthenticationFailure authenticationFailure) {
        log.error(authenticationFailure.getMessage());
        return ResponseEntity
                .status(401)
                .body(ResponseDto
                        .builder()
                        .code(401)
                        .success(false)
                        .message(authenticationFailure.getMessage())
                        .build());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDto<String>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest()
                .body(ResponseDto.<String>builder()
                        .code(404)
                        .message("User not found")
                        .data(null)
                        .success(false)
                        .build());
    }

    @ExceptionHandler(ContentDoesNotExistException.class)
    public ResponseEntity<ResponseDto<?>> handleContentDoesNotExistException(ContentDoesNotExistException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseDto
                        .builder()
                        .code(404)
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ImageDoesNotExistException.class)
    public ResponseEntity<ResponseDto<?>> handleImageDoesNotExistException(ImageDoesNotExistException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseDto
                        .builder()
                        .code(404)
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(FileNotSupportedException.class)
    public ResponseEntity<ResponseDto<?>> handleEmptyFileException(FileNotSupportedException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseDto
                        .builder()
                        .code(400)
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(PasswordNotAcceptedException.class)
    public ResponseEntity<ResponseDto<?>> handlePasswordExpiredException(PasswordNotAcceptedException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ResponseDto
                        .builder()
                        .code(403)
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(DuplicateKeyValue.class)
    public ResponseEntity<ResponseDto<?>> handleDuplicateKey(DuplicateKeyValue e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseDto
                        .builder()
                        .code(409)
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> handleUserNotFound(UserNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseDto
                        .builder()
                        .code(404)
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
