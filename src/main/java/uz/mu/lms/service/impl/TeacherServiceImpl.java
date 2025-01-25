package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.*;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.exceptions.UserAlreadyExistsException;
import uz.mu.lms.exceptions.UserNotFoundException;
import uz.mu.lms.model.Degree;
import uz.mu.lms.model.Department;
import uz.mu.lms.model.Teacher;
import uz.mu.lms.model.enums.RoleName;
import uz.mu.lms.repository.*;
import uz.mu.lms.service.TeacherService;
import uz.mu.lms.service.mapper.DegreeMapper;
import uz.mu.lms.service.mapper.TeacherMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final DegreeRepository degreeRepository;
    private final DegreeMapper degreeMapper;


    @Override
    public TeacherDto addTeacher(TeacherDto teacherDto) {
        if (teacherRepository.existsByUser_PhoneNumber(teacherDto.userDto().getPhoneNumber()))
            throw new UserAlreadyExistsException("Teacher with phone number " +
                    teacherDto.userDto().getPhoneNumber() + " already exists");

        if (teacherRepository.existsByUser_Username(teacherDto.userDto().getUsername()))
            throw new UserAlreadyExistsException("Teacher with username " +
                    teacherDto.userDto().getUsername() + " already exists");

        if (teacherRepository.existsByTeacherId(teacherDto.teacherId()))
            throw new UserAlreadyExistsException("Teacher with teacher Id " +
                    teacherDto.teacherId() + " already exists");

        // checking departments
        Set<Department> departments = teacherDto.departments().stream()
            .map(departmentDto ->
                    departmentRepository.findById(departmentDto.id())
                            .orElseThrow(() -> new ResourceNotFoundException("Department with id %d does not exist"
                                    .formatted(departmentDto.id()))))
                .collect(Collectors.toSet());

        // saving degrees
        Teacher teacher = teacherMapper.toEntity(teacherDto);
        teacher.setDepartments(departments);
        teacher.getUser().setPassword(teacherDto.userDto().getPassword());
        teacher.getUser().setRole(roleRepository.findByName(RoleName.ROLE_TEACHER.toString()));
        Teacher savedTeacher = teacherRepository.save(teacher);

        for (DegreeDto degreeDto : teacherDto.degrees()) {
            Degree entity = degreeMapper.toEntity(degreeDto);
            entity.setTeacher(savedTeacher);
            Degree save = degreeRepository.save(entity);
        }

        for (Department department : departments) {
            department.getTeachers().add(savedTeacher);
        }
        departmentRepository.saveAll(departments);

        return teacherMapper.toDto(savedTeacher);
     }

    @Override
    public void deleteTeacher(Integer id) {
        if (!teacherRepository.existsById(id))
            throw new UserNotFoundException("Teacher with id " + id + " does not exist");
        teacherRepository.deleteById(id);
    }

    @Override
    public List<TeacherDto> getTeachers(Pageable pageable) {
        return teacherRepository.findAll(pageable).stream().
                map(teacherMapper::toDto)
                .toList();
    }

    @Override
    public Teacher findTeacherById(Integer id) {
        return teacherRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Teacher with id %d not found".formatted(id))
        );
    }

    @Override
    public List<Teacher> findAllTeachersByIds(List<Integer> ids) {
        List<Teacher> teachers = new LinkedList<>();
        ids.forEach(id -> teachers.add(findTeacherById(id)));
        return teachers;
    }
}
