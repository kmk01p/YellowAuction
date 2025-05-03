package com.example.yellowaution.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;

    @Column(nullable = false)
    private String userType;

    public User(String username, String password, String role, String userType) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.userType = userType;
    }

    public User() {}
}
