package uz.mu.lms.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borrowing_seq_generator")
    @SequenceGenerator(name = "borrowing_seq_generator", sequenceName = "borrowing_seq", allocationSize = 1, initialValue = 999)
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PhysicalBook book;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    private LocalDateTime borrowedAt;
    private LocalDateTime dueDate;
    private LocalDateTime returnedAt;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}
