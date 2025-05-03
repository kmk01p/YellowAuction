package com.example.yellowaution.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileDto {
    private Long id;

    // 공통
    private String name;
    private String phone;
    private String email;

    // 기업 전용
    private String representative;
    private String companySize;
    private LocalDate establishedDate;
    private String mainIndustry;
    private String address;
    private Integer employees;
    private Long capital;
    private Long annualRevenue;
    private String homepageUrl;

    // 프리랜서 전용
    private String jobType;
    private String career;
    private String techStack;

    //소유자 식별을 위한 필드
    private Long userId;
}
