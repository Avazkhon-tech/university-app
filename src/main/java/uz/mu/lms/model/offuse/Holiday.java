package uz.mu.lms.model.offuse;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

//@Entity
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "holiday_seq_generator")
    @SequenceGenerator(name = "holiday_seq_generator", sequenceName = "holiday_seq", allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private LocalDate date;

    private String description;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}
