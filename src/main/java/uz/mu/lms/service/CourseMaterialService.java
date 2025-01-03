package uz.mu.lms.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.CourseMaterialDto;
import uz.mu.lms.dto.ResponseDto;

import java.util.List;

public interface CourseMaterialService {

    ResponseDto<List<CourseMaterialDto>> getCourseContents(Integer courseId);

    ResponseDto<String> addCourseMaterial(Integer courseId, String title, MultipartFile file);

    ResponseEntity<byte[]> getMaterialFile(Integer materialId);

    ResponseDto<?> addMaterialToExistingList(Integer materialId, MultipartFile file);
}
