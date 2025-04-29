package com.example.yellowaution.dto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProjectResponseDto {
    private Long id;
    private String title;
    private String category;
    private String status;
    private String clientUsername;
}
