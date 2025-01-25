package uz.mu.lms.service;

import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.CourseMaterialDto;

import java.util.List;

public interface CourseMaterialService {

    List<CourseMaterialDto> getCourseContents(Integer courseId);

    void addCourseMaterial(Integer courseId, String title, MultipartFile file);

    void addMaterialToExistingList(Integer materialId, MultipartFile file);
}
