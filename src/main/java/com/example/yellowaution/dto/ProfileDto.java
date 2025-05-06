package com.example.yellowaution.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * ProfileDto 클래스는 Profile 엔티티의 데이터를
 * API 요청·응답 시에 전송하기 위해 사용하는 Data Transfer Object입니다.
 *
 * <p>기업 사용자와 프리랜서 사용자의 공통/전용 프로필 정보를 모두 포함하며,
 * Controller 레이어에서 JSON 바인딩용으로 활용됩니다.</p>
 */
@Getter
@Setter
public class ProfileDto {

    /** 프로필 고유 식별자 (Profile 엔티티의 ID) */
    private Long id;

    // ─────────────────────────────────────────────────
    // 공통 필드 (기업/프리랜서 모두 사용)
    // ─────────────────────────────────────────────────

    /** 사용자 이름 (기업명 또는 개인명) */
    private String name;

    /** 연락처 전화번호 */
    private String phone;

    /** 이메일 주소 */
    private String email;


    // ─────────────────────────────────────────────────
    // 기업 전용 필드
    // ─────────────────────────────────────────────────

    /** 대표자 이름 */
    private String representative;

    /** 회사 규모 (예: "중소기업", "대기업" 등) */
    private String companySize;

    /** 설립일자 (LocalDate 타입으로 년-월-일 정보만 저장) */
    private LocalDate establishedDate;

    /** 주력 산업 분야 (예: "IT", "제조업") */
    private String mainIndustry;

    /** 회사 주소 */
    private String address;

    /** 직원 수 (Integer 타입으로 정수 값 저장) */
    private Integer employees;

    /** 자본금 (단위: 원, Long 타입) */
    private Long capital;

    /** 연간 매출액 (단위: 원, Long 타입) */
    private Long annualRevenue;

    /** 홈페이지 URL */
    private String homepageUrl;


    // ─────────────────────────────────────────────────
    // 프리랜서 전용 필드
    // ─────────────────────────────────────────────────

    /** 희망 직무 유형 (예: "개발자", "디자이너" 등) */
    private String jobType;

    /** 경력 요약 또는 주요 경력 사항 */
    private String career;

    /** 기술 스택 목록 (예: ["Java", "Spring", "React"]) */
    private List<String> techStack;


    // ─────────────────────────────────────────────────
    // 연관 필드
    // ─────────────────────────────────────────────────

    /** 이 프로필을 소유한 사용자(User)의 고유 식별자 */
    private Long userId;
}
