package uz.mu.lms.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import uz.mu.lms.model.enums.LessonGradingType;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LessonGrade extends Grade {

    @ManyToOne
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    private LessonGradingType gradingType;

}
