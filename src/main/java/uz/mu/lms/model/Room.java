package uz.mu.lms.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

//@Entity
//@Setter
//@Getter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq_generator")
    @SequenceGenerator(name = "room_seq_generator", sequenceName = "class_room_seq", allocationSize = 1)
    private Long id;

    private String roomNumber;

    @OneToMany(mappedBy = "room")
    private List<Lesson> lessons;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}