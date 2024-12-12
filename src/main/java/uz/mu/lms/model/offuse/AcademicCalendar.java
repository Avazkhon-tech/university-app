package uz.mu.lms.model.offuse;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import uz.mu.lms.model.Faculty;

import java.time.LocalDate;
import java.time.LocalDateTime;

//@Entity
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
public class AcademicCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "academic_calendar_seq_generator")
    @SequenceGenerator(name = "academic_calendar_seq_generator", sequenceName = "academic_calendar_seq", allocationSize = 1)
    private Integer id;

    private String name;

    @ManyToOne
    private Faculty faculty;

    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

    private LocalDate midtermStartDate;
    private LocalDate midtermEndDate;

    private LocalDate finalStartDate;
    private LocalDate finalEndDate;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}
