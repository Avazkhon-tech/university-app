package uz.mu.lms.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.mu.lms.model.enums.LessonType;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LessonTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_teacher_seq_generator")
    @SequenceGenerator(name = "course_teacher_seq_generator", sequenceName = "course_teacher_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Course course;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Teacher teacher;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TimeSlot timeSlot;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Enumerated(EnumType.STRING)
    private LessonType lessonType;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}
