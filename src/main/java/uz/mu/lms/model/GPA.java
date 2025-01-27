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
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class GPA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gpa_seq_generator")
    @SequenceGenerator(name = "gpa_seq_generator", sequenceName = "gpa_seq", allocationSize = 1)
    private Integer Id;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Student student;

    private Double score;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;
    @LastModifiedBy
    private Integer updatedBy;
}