package uz.mu.lms.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq_generator")
    @SequenceGenerator(name = "role_seq_generator", sequenceName = "role_seq", allocationSize = 1)
    private Integer id;
    private String name;

    @ManyToMany
    private List<Privilege> privileges;
}
