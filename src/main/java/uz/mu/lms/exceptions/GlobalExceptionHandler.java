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

    @ExceptionHandler(AuthenticationFailureException.class)
    public ResponseEntity<ResponseDto<?>> handleAuthenticationFailure(AuthenticationFailureException authenticationFailureException) {
        log.error(authenticationFailureException.getMessage());
        return ResponseEntity
                .status(401)
                .body(ResponseDto
                        .builder()
                        .code(401)
                        .success(false)
                        .message(authenticationFailureException.getMessage())
                        .build());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDto<String>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(404)
                .body(ResponseDto.<String>builder()
                        .code(404)
                        .message("User not found")
                        .data(null)
                        .success(false)
                        .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> handleContentDoesNotExistException(ResourceNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseDto
                        .builder()
                        .code(409)
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseDto<?>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseDto
                        .builder()
                        .code(409)
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
    public ResponseEntity<ResponseDto<?>> handlePasswordNotAcceptedException(PasswordNotAcceptedException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseDto
                        .builder()
                        .code(400)
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(DuplicateKeyValueException.class)
    public ResponseEntity<ResponseDto<?>> handleDuplicateKey(DuplicateKeyValueException e) {
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

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseDto<Object>> handleUserAlreadyExistsExceptions(UserAlreadyExistsException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseDto
                        .builder()
                        .code(409)
                        .message(e.getMessage())
                        .build()
        );
    }
}
