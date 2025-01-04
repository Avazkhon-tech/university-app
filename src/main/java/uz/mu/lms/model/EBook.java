package uz.mu.lms.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class EBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ebook_seq_generator")
    @SequenceGenerator(name = "ebook_seq_generator", sequenceName = "ebook_seq", allocationSize = 1)
    private Integer id;

    private String title;

    private String author;

    private String description;

    private String isbn;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Attachment bookFile;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Attachment bookCoverImage;

    @ManyToOne
    private BookCategory bookCategory;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;
}
