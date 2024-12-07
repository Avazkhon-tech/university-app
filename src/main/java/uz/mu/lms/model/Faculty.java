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
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faculty_seq_generator")
    @SequenceGenerator(name = "faculty_seq_generator", sequenceName = "faculty_seq", allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private Teacher deanOfFaculty;

    private String phoneNumber;

    @OneToMany
    private List<Department> departments;

    @CreatedDate
    private LocalDateTime establishedDate;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}
