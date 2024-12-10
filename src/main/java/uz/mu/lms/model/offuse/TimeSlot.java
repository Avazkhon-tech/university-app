package uz.mu.lms.model.offuse;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

//@Entity
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timeslot_seq_generator")
    @SequenceGenerator(name = "timeslot_seq_generator", sequenceName = "timeslot_seq", allocationSize = 1)
    private Integer id;

    private Integer orderNumber;

    private LocalTime startTime;

    private Duration duration;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}
