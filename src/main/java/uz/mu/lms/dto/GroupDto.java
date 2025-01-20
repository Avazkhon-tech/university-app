package uz.mu.lms.dto;

import java.util.List;

public record GroupDto(

        Integer id,
        String name,
        Integer departmentId,
        List<Integer> courseIds
) {}
