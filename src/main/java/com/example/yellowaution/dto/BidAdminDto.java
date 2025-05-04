package com.example.yellowaution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class BidAdminDto {
    private Long id;
    private String boardTitle;
    private String bidderUsername;
    private Long amount;
    private String createdAt;
}
