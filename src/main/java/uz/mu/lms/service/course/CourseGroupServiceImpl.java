package uz.mu.lms.service.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.CourseGroupDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.model.Course;
import uz.mu.lms.model.CourseGroup;
import uz.mu.lms.projection.CourseGroupProjection;
import uz.mu.lms.repository.CourseGroupRepository;
import uz.mu.lms.repository.CourseRepository;
import uz.mu.lms.service.mapper.CourseGroupMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseGroupServiceImpl implements CourseGroupService {

    private final CourseGroupRepository courseGroupRepository;
    private final CourseGroupMapper courseGroupMapper;
    private final CourseRepository courseRepository;

    @Override
    public List<CourseGroupProjection> getAllGroups(Integer courseId) {

        return courseGroupRepository.findByCourseId(courseId);
    }

    @Override
    public boolean createGroup(CourseGroupDto courseGroupDto, Integer courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course with id %s not found"
                        .formatted(courseId)));

        CourseGroup entity = courseGroupMapper.toEntity(courseGroupDto);
        entity.setCourse(course);

        courseGroupRepository.save(entity);
        return true;
    }
}
