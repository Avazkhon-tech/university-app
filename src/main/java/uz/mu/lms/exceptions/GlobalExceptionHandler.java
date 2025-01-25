package uz.mu.lms.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.mu.lms.dto.ResponseDto;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationFailureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseDto<?> handleAuthenticationFailure(AuthenticationFailureException authenticationFailureException) {
        log.info(authenticationFailureException.getMessage());
        return ResponseDto.builder()
                .code(401)
                .success(false)
                .message(authenticationFailureException.getMessage())
                .build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDto<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.info(e.getMessage());
        return ResponseDto.<String>builder()
                .code(404)
                .message("User not found")
                .success(false)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseDto<?> handleContentDoesNotExistException(ResourceNotFoundException e) {
        log.info(e.getMessage());
        return ResponseDto.builder()
                .code(409)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseDto<?> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        log.info(e.getMessage());
        return ResponseDto.builder()
                .code(409)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(ImageDoesNotExistException.class)
    public ResponseDto<?> handleImageDoesNotExistException(ImageDoesNotExistException e) {
        log.info(e.getMessage());
        return ResponseDto.builder()
                .code(404)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(FileNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<?> handleEmptyFileException(FileNotSupportedException e) {
        log.info(e.getMessage());
        return ResponseDto.builder()
                .code(400)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(PasswordNotAcceptedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<?> handlePasswordNotAcceptedException(PasswordNotAcceptedException e) {
        log.info(e.getMessage());
        return ResponseDto.builder()
                .code(400)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(DuplicateKeyValueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseDto<?> handleDuplicateKey(DuplicateKeyValueException e) {
        log.info(e.getMessage());
        return ResponseDto.builder()
                .code(409)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDto<?> handleUserNotFound(UserNotFoundException e) {
        log.info(e.getMessage());
        return ResponseDto.builder()
                .code(404)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.info(ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseDto.<Map<String, String>>builder()
                .code(400)
                .message("Invalid field values provided")
                .success(false)
                .data(errors)
                .build();
    }


    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseDto<Object> handleUserAlreadyExistsExceptions(UserAlreadyExistsException e) {
        log.info(e.getMessage());
        return ResponseDto.builder()
                .code(409)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(CouldNotProcessRequestException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto<?> handleCouldNotProcessRequestException(CouldNotProcessRequestException e) {
        log.info(e.getMessage());
        return ResponseDto.builder()
                .code(500)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(StudentIsNotInUniversityZoneException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseDto<?> handleCouldNotProcessRequestException(StudentIsNotInUniversityZoneException e) {
        log.info(e.getMessage());
        return ResponseDto.builder()
                .code(409)
                .message(e.getMessage())
                .build();
    }
}
