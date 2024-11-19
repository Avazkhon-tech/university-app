package uz.mu.lms.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity(name = "news")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_seq_generator")
    @SequenceGenerator(name = "news_seq_generator", sequenceName = "news_seq", allocationSize = 1)
    private Integer Id;

    private String title;

    private String body;

    private String location;

    private String eventTime;

    private Integer contentId;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;
    @LastModifiedBy
    private Integer updatedBy;
}