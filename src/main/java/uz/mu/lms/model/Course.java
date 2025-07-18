package uz.mu.lms.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq_generator")
    @SequenceGenerator(name = "course_seq_generator", sequenceName = "course_seq", allocationSize = 1)
    private Integer id;

    private String title;

    private String description;

    private String taughtLanguage;

    private Integer semester;

    private Integer year;

    private Integer ECTSCredits;

    private Integer creditHours;

    private Integer creditPoints;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private GradingScale gradingScale;

    @OneToMany(mappedBy = "course")
    private List<CourseMaterial> courseMaterials;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}
