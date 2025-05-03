package com.example.yellowaution.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardDto {
    private Long id;
    private String title;
    private LocalDate dueDate;
    private String description;
    private List<String> technologies;
    private Integer recruitPeriod;
    private Long startPrice;
    private Long currentPrice;
    private String status;
    private LocalDateTime createdAt;

    private Long userId;
    private String username;

    public BoardDto(Long id, String title, LocalDate dueDate, String description,
                    List<String> technologies, Integer recruitPeriod, Long startPrice,
                    Long currentPrice, String status, LocalDateTime createdAt,
                    Long userId, String username) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.description = description;
        this.technologies = technologies;
        this.recruitPeriod = recruitPeriod;
        this.startPrice = startPrice;
        this.currentPrice = currentPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.userId = userId;
        this.username = username;
    }
}
