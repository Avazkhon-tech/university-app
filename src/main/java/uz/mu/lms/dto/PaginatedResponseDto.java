package uz.mu.lms.dto;

import lombok.Builder;

@Builder
public record PaginatedResponseDto<T> (
        Integer code,
        String message,
        boolean success,
        int page,
        int size,
        T data
) {

    /*
    Paginated data success response
    */
    public static <T> PaginatedResponseDto<T> success(int page, int size, T data) {
        return new PaginatedResponseDto<>(200, "OK", true, page, size, data);
    }

    /*
    Paginated data success response with a custom message
    */
    public static <T> PaginatedResponseDto<T> success(int page, int size, T data, String message) {
        return new PaginatedResponseDto<>(200, message, true, page, size, data);
    }

    /*
    Paginated success response with message only (no data)
    */
    public static <T> PaginatedResponseDto<T> success(int page, int size, String message) {
        return new PaginatedResponseDto<>(200, message, true, page, size, null);
    }

    /*
    Error response for paginated data
    */
    public static <T> PaginatedResponseDto<T> error(Integer code, String message) {
        return new PaginatedResponseDto<>(code, message, false, 0, 0, null);
    }

    /*
    Error response for paginated data with custom page/size
    */
    public static <T> PaginatedResponseDto<T> error(Integer code, String message, int page, int size) {
        return new PaginatedResponseDto<>(code, message, false, page, size, null);
    }
}
