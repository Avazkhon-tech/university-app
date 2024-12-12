package uz.mu.lms.model.offuse;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import uz.mu.lms.model.Room;

import java.time.LocalDateTime;
import java.util.List;

//@Entity
//@Setter
//@Getter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_generator")
    @SequenceGenerator(name = "building_generator", sequenceName = "buildings_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String address;

    @OneToMany
    private List<Room> classrooms;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}