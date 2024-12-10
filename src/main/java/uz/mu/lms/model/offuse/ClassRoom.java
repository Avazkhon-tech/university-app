package uz.mu.lms.model.offuse;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

//@Entity
//@Setter
//@Getter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_room_generator")
    @SequenceGenerator(name = "class_room_generator", sequenceName = "class_room_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String address;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}