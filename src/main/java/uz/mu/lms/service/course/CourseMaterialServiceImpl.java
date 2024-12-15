package uz.mu.lms.service.course;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
import uz.mu.lms.service.content.ContentService;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CourseMaterialServiceImpl implements CourseMaterialService {

    private final CourseRepository courseRepository;
    private final CourseMaterialMapper courseMaterialMapper;
    private final ContentService contentService;
    private final CourseMaterialRepository courseMaterialRepository;

    @Override
    public ResponseDto<List<CourseMaterialDto>> getCourseContents(Integer courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course with id %d not found"));

        List<CourseMaterial> courseMaterials = course.getCourseMaterials();

        List<CourseMaterialDto> list = courseMaterials
                .stream()
                .map(courseMaterialMapper::toDto)
                .toList();

        return ResponseDto.<List<CourseMaterialDto>>builder()
                .code(200)
                .message("OK")
                .success(true)
                .data(list)
                .build();
    }

    @Override
    public ResponseDto<String> addCourseMaterial(Integer courseId, String title,  MultipartFile file) {
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

        return ResponseDto.<String>builder()
                .code(200)
                .message("OK")
                .success(true)
                .build();
    }

    @Override
    public ResponseEntity<byte[]> getMaterialFile(Integer materialId) {
        return contentService.retrieveContent(materialId);
    }
}
