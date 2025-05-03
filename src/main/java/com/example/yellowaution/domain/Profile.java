package com.example.yellowaution.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;

    private String representative;
    private String companySize;
    private LocalDate establishedDate;
    private String mainIndustry;
    private String address;
    private Integer employees;
    private Long capital;
    private Long annualRevenue;
    private String homepageUrl;

    private String jobType;
    private String career;

    @ElementCollection
    @CollectionTable(name = "profile_tech_stack", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "tech")
    private List<String> techStack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
