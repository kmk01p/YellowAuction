package com.example.yellowaution.dto;

import com.example.yellowaution.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
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

    public static BoardDto from(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .dueDate(board.getDueDate())
                .description(board.getDescription())
                .technologies(board.getTechnologies())
                .recruitPeriod(board.getRecruitPeriod())
                .startPrice(board.getStartPrice())
                .currentPrice(board.getCurrentPrice())
                .status(board.getStatus())
                .createdAt(board.getCreatedAt())
                .userId(board.getUser() != null ? board.getUser().getId() : null)
                .username(board.getUser() != null ? board.getUser().getUsername() : null)
                .build();
    }

}
