package com.example.yellowaution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * UserAdminDto 클래스는 관리자 화면에서 전체 사용자 정보를 조회할 때
 * 사용자 엔티티의 주요 속성만을 가공하여 전송하기 위한 DTO입니다.
 *
 * <p>주요 필드:
 * <ul>
 *   <li>id          : 사용자 고유 식별자</li>
 *   <li>username    : 로그인에 사용되는 아이디</li>
 *   <li>role        : 부여된 권한 (예: ADMIN, EMPLOYER, FREELANCER)</li>
 *   <li>userType    : 사용자 유형 (기업/프리랜서 구분)</li>
 *   <li>email       : 회원가입 또는 연락용 이메일 주소</li>
 *   <li>createdAt   : 계정 생성 일시 (예: "2025-05-06T14:30:00")</li>
 * </ul>
 * </p>
 */
@Data
@AllArgsConstructor
public class UserAdminDto {

    /** 사용자 고유 식별자 (User 엔티티의 ID) */
    private Long id;

    /** 로그인에 사용되는 고유 아이디 */
    private String username;

    /** Spring Security 권한 정보 (예: "ADMIN", "EMPLOYER", "FREELANCER") */
    private String role;

    /** 비즈니스 로직 구분용 사용자 타입 (예: "EMPLOYER" 또는 "FREELANCER") */
    private String userType;

    /** 회원가입 또는 연락용 이메일 주소 */
    private String email;

    /**
     * 계정 생성 일시
     * - ISO 8601 문자열 포맷 권장 (예: "2025-05-06T14:30:00")
     * - Jackson 직렬화 시 패턴 지정 필요 시 @JsonFormat 활용 가능
     */
    private String createdAt;
}
