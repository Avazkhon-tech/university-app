package uz.mu.lms.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CourseTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_teacher_seq_generator")
    @SequenceGenerator(name = "course_teacher_seq_generator", sequenceName = "course_teacher_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Group group;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}
