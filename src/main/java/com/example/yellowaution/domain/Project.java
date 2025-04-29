package com.example.yellowaution.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String category;
    private String description;
    private String status; // 진행중, 모집완료 등

    @ManyToOne
    private User client;
}
