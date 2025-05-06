package com.example.yellowaution.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Profile 엔티티는 기업·프리랜서 사용자 프로필 정보를 저장합니다.
 *
 * <p>주요 역할:
 * <ul>
 *   <li>회사/개인 기본 정보(name, phone, email 등) 관리</li>
 *   <li>사업 정보(representative, companySize, establishedDate 등) 관리</li>
 *   <li>주요 기술 스택(techStack) 목록 저장</li>
 *   <li>관련 User 엔티티와 다대일 관계 설정</li>
 * </ul>
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile {

    /**
     * 프로필 고유 식별자(ID)
     * - @Id: 기본 키(primary key) 지정
     * - @GeneratedValue: IDENTITY 전략으로 DB에서 자동 증가
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 프로필 소유자의 이름(기업명 또는 개인명)
     * - null 허용 여부는 비즈니스 요구에 따라 추가 검증 가능
     */
    private String name;

    /**
     * 연락처(전화번호)
     * - 국제 전화번호 형식 등 유효성 검사는 서비스 레이어 또는 DTO에서 수행
     */
    private String phone;

    /**
     * 이메일 주소
     * - 로그인·알림용 이메일과 연동되며, 형식 검증은 DTO 레벨에서 수행
     */
    private String email;

    /**
     * 대표자 이름(기업의 경우 대표자·프리랜서는 본인)
     */
    private String representative;

    /**
     * 회사 규모(예: "중소기업", "대기업", "프리랜서" 등)
     */
    private String companySize;

    /**
     * 설립(창립)일자
     * - LocalDate 타입으로 연-월-일 정보만 저장
     */
    private LocalDate establishedDate;

    /**
     * 주력 산업 분야(예: "IT", "제조업", "컨설팅" 등)
     */
    private String mainIndustry;

    /**
     * 사업장 또는 거주지 주소
     */
    private String address;

    /**
     * 직원 수(정수)
     */
    private Integer employees;

    /**
     * 자본금(단위: 원)
     */
    private Long capital;

    /**
     * 연간 매출액(단위: 원)
     */
    private Long annualRevenue;

    /**
     * 회사 또는 개인 홈페이지 URL
     * - 올바른 URL 형식 검증은 DTO 또는 클라이언트에서 수행
     */
    private String homepageUrl;

    /**
     * 채용·구인 타입(예: "정규직", "계약직", "프리랜서" 등)
     */
    private String jobType;

    /**
     * 경력 요약 또는 주요 경력 사항
     * - 자유 서술형 텍스트로 길이 제한은 별도 설정 필요
     */
    private String career;

    /**
     * 주요 기술 스택 목록
     * - @ElementCollection: 문자열 리스트를 별도 테이블(profile_tech_stack)에 저장
     * - @CollectionTable: 테이블 명과 외래 키(profile_id) 지정
     */
    @ElementCollection
    @CollectionTable(
            name = "profile_tech_stack",
            joinColumns = @JoinColumn(name = "profile_id")
    )
    @Column(name = "tech")
    private List<String> techStack;

    /**
     * 이 프로필을 소유한 사용자(User)와의 다대일 관계
     * - FetchType.LAZY: 실제로 조회 시점에 로딩
     * - nullable = false: 반드시 로그인된 사용자와 연관되어야 함
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
