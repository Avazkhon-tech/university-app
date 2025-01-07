package uz.mu.lms.dto;

import lombok.Builder;

@Builder
public record PaginatedResponseDto<T>  (
    Integer code,
    String message,
    boolean success,
    int page,
    int size,
    T data
) {}
