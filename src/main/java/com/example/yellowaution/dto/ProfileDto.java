package com.example.yellowaution.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileDto {
    private Long id;

    private String companyName;
    private String representative;
    private String companySize;
    private LocalDate establishedDate;
    private String mainIndustry;
    private String address;
    private Integer employees;
    private Long capital;
    private Long annualRevenue;
    private String homepageUrl;
    private String phone;
    private String email;

    private Long userId;  // ✅ 소유자 식별을 위한 필드
}
