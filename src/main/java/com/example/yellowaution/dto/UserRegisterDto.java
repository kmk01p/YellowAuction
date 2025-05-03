package com.example.yellowaution.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRegisterDto {
    private String username;
    private String password;
    private String role;
    private String userType;
    private ProfileDto profile;
}
