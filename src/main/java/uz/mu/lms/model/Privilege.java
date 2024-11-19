package uz.mu.lms.model;

import jakarta.persistence.*;

@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "privilege_seq_generator")
    @SequenceGenerator(name = "privilege_seq_generator", sequenceName = "privilege_seq", allocationSize = 1)
    private Integer id;

    private String name;
}
