package uz.mu.lms.dto;

import lombok.Builder;

@Builder
public record ResponseDto<T> (
    Integer code,
    String message,
    T data,
    boolean success
) {}