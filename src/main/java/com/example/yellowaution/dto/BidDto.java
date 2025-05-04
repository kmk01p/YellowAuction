package com.example.yellowaution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BidDto {
    private Long id;
    private String boardTitle;
    private String bidderUsername;
    private Long amount;
    private LocalDateTime createdAt;
}
