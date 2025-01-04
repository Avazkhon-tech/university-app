package uz.mu.lms.dto;

import java.time.LocalDateTime;

public record BorrowingDto(
    Integer id,
    Integer bookId,
    String username,
    LocalDateTime borrowedAt,
    LocalDateTime dueDate,
    LocalDateTime returnedAt
) {}
