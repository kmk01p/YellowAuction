package com.example.yellowaution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAdminDto {
    private Long id;
    private String username;
    private String role;
    private String userType;
    private String email;
    private String createdAt;
}
