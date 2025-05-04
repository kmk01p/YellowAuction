package com.example.yellowaution.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)

public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이걸 명시적으로 설정
    private Long id;

    @ManyToOne
    private Board board;

    @ManyToOne
    private User user;

    private Long amount;

    @CreatedDate
    private LocalDateTime createdAt;
}
