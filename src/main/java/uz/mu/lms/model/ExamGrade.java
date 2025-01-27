package uz.mu.lms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import uz.mu.lms.model.enums.ExamType;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ExamGrade extends Grade {

    @ManyToOne
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    private ExamType examType;

}
