package uz.mu.lms.dto;

import java.time.LocalTime;

public record TimeslotDto(
        Integer id,
        Integer orderNumber,
        LocalTime startTime,
        LocalTime endTime
) {}
