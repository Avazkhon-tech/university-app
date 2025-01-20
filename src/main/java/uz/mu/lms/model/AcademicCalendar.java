package uz.mu.lms.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AcademicCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "a_calendar_seq_generator")
    @SequenceGenerator(name = "a_calendar_seq_generator", sequenceName = "a_calendar_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    private Department department;

    private LocalDate sem1StartDate;
    private LocalDate sem1MidtermStart;
    private LocalDate sem1MidtermEnd;
    private LocalDate sem1FinalStart;
    private LocalDate sem1FinalEnd;
    private LocalDate sem1EndDate;

    private LocalDate sem2StartDate;
    private LocalDate sem2MidtermStart;
    private LocalDate sem2MidtermEnd;
    private LocalDate sem2FinalStart;
    private LocalDate sem2FinalEnd;
    private LocalDate sem2EndDate;

    private boolean areLessonsGenerated;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}
