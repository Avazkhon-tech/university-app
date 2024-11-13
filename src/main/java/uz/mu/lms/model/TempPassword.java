package uz.mu.lms.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TempPassword {

    private String generatedPassword;

    private LocalDateTime expirationDate;

    @Id
    private Long userId;
}
