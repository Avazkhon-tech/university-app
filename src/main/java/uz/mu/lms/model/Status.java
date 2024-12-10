package uz.mu.lms.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_seq_generator")
    @SequenceGenerator(name = "status_seq_generator", sequenceName = "status_seq", allocationSize = 1)
    private Integer id;

    private String name;
}
