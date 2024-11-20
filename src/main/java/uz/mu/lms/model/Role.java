package uz.mu.lms.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
//@EntityListeners(AuditingEntityListener.class)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq_generator")
    @SequenceGenerator(name = "role_seq_generator", sequenceName = "role_seq", allocationSize = 1)
    private Integer id;

    private String name;

    @ManyToMany
    private List<Authority> authorities;

//    @CreatedDate
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    private LocalDateTime updatedAt;
//
//    @CreatedBy
//    private Integer createdBy;
//
//    @LastModifiedBy
//    private Integer updatedBy;
}
