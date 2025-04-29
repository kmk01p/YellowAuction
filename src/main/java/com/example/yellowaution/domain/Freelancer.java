package com.example.yellowaution.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Freelancer {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String skill;
    private int experience;

    @OneToOne
    private User user;
}
