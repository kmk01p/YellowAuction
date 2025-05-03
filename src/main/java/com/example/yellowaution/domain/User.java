package com.example.yellowaution.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
@Getter @Setter
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String role;

    @Column(nullable = false)
    private String userType;

    // ✅ 커스텀 생성자 추가
    public User(String username, String password, String role, String userType) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.userType = userType;
    }

    // 기본 생성자도 반드시 필요함 (JPA가 사용)
    public User() {
    }
}
