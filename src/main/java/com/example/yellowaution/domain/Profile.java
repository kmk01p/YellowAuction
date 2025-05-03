package com.example.yellowaution.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String representative;

    @Column(nullable = false)
    private String companySize;

    @Column(nullable = false)
    private LocalDate establishedDate;

    @Column(nullable = false)
    private String mainIndustry;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(nullable = false)
    private Integer employees;

    @Column(nullable = false)
    private Long capital;

    @Column(nullable = false)
    private Long annualRevenue;

    @Column(nullable = false)
    private String homepageUrl;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
