package uz.mu.lms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class GradingScale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grading_scale_seq_generator")
    @SequenceGenerator(name = "grading_scale_seq_generator", sequenceName = "grading_scale_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    private Course course;

    @NotNull
    private Integer pass;

    @NotNull
    private Integer merit;

    @NotNull
    private Integer distinction;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}
