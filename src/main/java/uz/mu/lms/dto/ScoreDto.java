package uz.mu.lms.dto;

import lombok.Builder;

@Builder
public record ScoreDto(

    Integer earned,
    Integer total
) {}
