package com.example.yellowaution.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // — 공통 필드 (필수) —
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    // — 기업 전용 필드 (옵션) —
    @Column(nullable = true)
    private String representative;

    @Column(nullable = true)
    private String companySize;

    @Column(nullable = true)
    private LocalDate establishedDate;

    @Column(nullable = true)
    private String mainIndustry;

    @Column(nullable = true, length = 500)
    private String address;

    @Column(nullable = true)
    private Integer employees;

    @Column(nullable = true)
    private Long capital;

    @Column(nullable = true)
    private Long annualRevenue;

    @Column(nullable = true)
    private String homepageUrl;

    // — 프리랜서 전용 필드 (옵션) —
    @Column(nullable = true)
    private String jobType;

    @Column(nullable = true)
    private String career;

    @Column(nullable = true, length = 500)
    private String techStack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
