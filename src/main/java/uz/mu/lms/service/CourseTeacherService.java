package uz.mu.lms.service;

import uz.mu.lms.dto.CourseTeacherDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.model.CourseTeacher;

public interface CourseTeacherService {

    ResponseDto<CourseTeacher> addCourseInstructor(CourseTeacherDto courseTeacherDto);
 }
