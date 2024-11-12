package uz.mu.lms.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "news")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String title;

    private String body;

    private String location;

    private String eventTime;

    @OneToOne(cascade = CascadeType.ALL)
    private Content content;
}