package uz.mu.lms.model;

import lombok.*;

// make it jpa entity later
@Builder
public record Building (
        String name,
        double latitude,
        double longitude,
        double radiusInMeters
) {}