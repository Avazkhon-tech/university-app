package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.CourseMaterialDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.model.Course;
import uz.mu.lms.model.CourseMaterial;
import uz.mu.lms.repository.CourseMaterialRepository;
import uz.mu.lms.repository.CourseRepository;
import uz.mu.lms.service.ContentService;
import uz.mu.lms.service.CourseMaterialService;
import uz.mu.lms.service.mapper.CourseMaterialMapper;

import java.util.List;

@Service
@RequiredArgsConstructor


public class CourseMaterialServiceImpl implements CourseMaterialService {

    private final CourseRepository courseRepository;
    private final CourseMaterialRepository courseMaterialRepository;
    private final CourseMaterialMapper courseMaterialMapper;
    private final ContentService contentService;

    @Override
    public List<CourseMaterialDto> getCourseContents(Integer courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course with id %d not found"
                        .formatted(courseId)));

        List<CourseMaterial> courseMaterials = course.getCourseMaterials();

        return courseMaterials
                .stream()
                .map(courseMaterialMapper::toDto)
                .toList();
    }

    @Override
    public void addCourseMaterial(Integer courseId, String title, MultipartFile file) {
        Attachment attachment = contentService.createContent(file);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course with id %d not found"
                        .formatted(courseId)));

        CourseMaterial courseMaterial = CourseMaterial
                .builder()
                .title(title)
                .course(course)
                .attachments(List.of(attachment))
                .build();
        courseMaterialRepository.save(courseMaterial);
    }

    @Override
    public void addMaterialToExistingList(Integer materialId, MultipartFile file) {
        CourseMaterial courseMaterial = courseMaterialRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("Material list with id %d not found"
                        .formatted(materialId)));

        Attachment attachment = contentService.createContent(file);
        courseMaterial.getAttachments().add(attachment);
        courseMaterialRepository.save(courseMaterial);
    }
}
