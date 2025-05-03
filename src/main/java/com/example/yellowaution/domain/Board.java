package com.example.yellowaution.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private LocalDate dueDate;

    @Column(length = 2000)
    private String description;

    @ElementCollection
    @CollectionTable(name = "board_technologies", joinColumns = @JoinColumn(name = "board_id"))
    @Column(name = "technology")
    private List<String> technologies;

    private Integer recruitPeriod;

    @Column(nullable = false)
    private Long startPrice;

    @Column(nullable = false)
    private Long currentPrice;

    @Column(nullable = false)
    private String status;  // "구인중" 또는 "마감"

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


}