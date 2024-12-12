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
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq_generator")
    @SequenceGenerator(name = "student_seq_generator", sequenceName = "student_seq", allocationSize = 1)
    private Integer id;

    @Column(unique = true)
    private String studentId;

    private Integer photoAttachmentId;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private User user;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    @ManyToMany(mappedBy = "students")
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "student")
    private List<Attendance> attendances;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}
