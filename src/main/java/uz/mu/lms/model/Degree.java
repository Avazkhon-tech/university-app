package uz.mu.lms.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Degree {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "degree_seq_generator")
    @SequenceGenerator(name = "degree_seq_generator", sequenceName = "degree_seq", allocationSize = 1)
    private Integer id;

    private Integer degreeName;

    private String universityName;

    private Integer yearOfCompletion;
}
