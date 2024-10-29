package uz.mu.lms.dto;

import lombok.Builder;

@Builder
public record PaginatedResponseDto<T>  (
    Integer code,
    String message,
    T data,
    boolean success,
    int page,
    int size
) {}
