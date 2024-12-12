package uz.mu.lms.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import uz.mu.lms.model.enums.AttendanceStatus;

import java.time.LocalDateTime;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendance_seq_generator")
    @SequenceGenerator(name = "attendance_seq_generator", sequenceName = "attendance_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @CreatedDate
    private LocalDateTime attendanceDate;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}
