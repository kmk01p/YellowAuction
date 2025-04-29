package com.example.yellowaution.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String role; // ROLE_CLIENT, ROLE_FREELANCER

}
