package uz.mu.lms.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.mu.lms.model.enums.ExamType;

import javax.naming.Name;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exam_seq_generator")
    @SequenceGenerator(name = "exam_seq_generator", sequenceName = "exam_seq", allocationSize = 1)
    private Integer id;

    private LocalDateTime localDateTime;

    private Integer durationMinutes;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Course course;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "exam_supervisors",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private List<Teacher> teachers;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "exam_students",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(EnumType.STRING)
    private ExamType examType;

    private Integer fee;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}
