package uz.mu.lms.service;


import uz.mu.lms.dto.LectureTemplateDto;
import uz.mu.lms.dto.LessonTemplateDto;

public interface LessonTemplateService {


    void createTemplate(LessonTemplateDto lessonTemplateDto, Integer groupId);

    void createTemplate(LectureTemplateDto lectureTemplateDto);
}
