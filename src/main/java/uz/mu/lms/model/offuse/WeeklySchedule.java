package uz.mu.lms.model.offuse;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.mu.lms.model.enums.DayOfWeek;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class WeeklySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weekly_schedule_seq_generator")
    @SequenceGenerator(name = "weekly_schedule_seq_generator", sequenceName = "weekly_schedule_seq", allocationSize = 1)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @ManyToOne
    private TimeSlot timeSlot;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;


}
