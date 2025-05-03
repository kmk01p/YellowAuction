package com.example.yellowaution.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor
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
}
