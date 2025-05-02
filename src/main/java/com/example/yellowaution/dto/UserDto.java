// UserDto.java  (보안상 password는 제외)
package com.example.yellowaution.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private Long id;
    private String username;
    private String role;
    private String userType;  // "EMPLOYER" or "FREELANCER"

    public UserDto(Long id, String username, String role, String userType) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.userType = userType;

    }
}
