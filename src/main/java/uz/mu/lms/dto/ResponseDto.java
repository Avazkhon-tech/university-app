package uz.mu.lms.dto;

import lombok.Builder;

@Builder
public record ResponseDto<T> (
    Integer code,
    boolean success,
    String message,
    T data
) {

    /*
    data only
     */
    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(200, true, "OK", data);
    }

    public static <T> ResponseDto<T> success(T data, String message) {
        return new ResponseDto<>(200, true, message, data);
    }

    /*
    message only
     */
    public static <T> ResponseDto<T> success(String message) {
        return new ResponseDto<>(200, true, message, null);
    }

    public static <T> ResponseDto<T> error(Integer code, String message) {
        return new ResponseDto<>(code, false, message, null);
    }
}