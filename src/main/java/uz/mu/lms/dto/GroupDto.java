package uz.mu.lms.dto;

import uz.mu.lms.model.CourseTeacher;

import java.util.List;

public record GroupDto(

        Integer id,
        String name,
        Integer departmentId,
        List<CourseTeacherDto> coursesAndTeachers
) {}
