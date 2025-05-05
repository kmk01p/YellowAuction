package com.example.yellowaution.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue
    private Long id;

    private String token;            // UUID.randomUUID().toString()
    private LocalDateTime expiry;    // 생성 시점 + 유효시간(예: 1시간)

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}