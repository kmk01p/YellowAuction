package com.example.yellowaution.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * UserDto 클래스는 클라이언트에 전송할 사용자 정보를 담는 Data Transfer Object입니다.
 * 보안상의 이유로 비밀번호(password)는 포함되지 않습니다.
 *
 * <p>주요 필드:
 * <ul>
 *   <li>id       : 사용자 고유 식별자</li>
 *   <li>username : 로그인에 사용되는 아이디</li>
 *   <li>role     : Spring Security 권한 정보 (예: "ADMIN", "EMPLOYER", "FREELANCER")</li>
 *   <li>userType : 비즈니스 로직 구분용 사용자 유형
 *                  (예: "EMPLOYER" 또는 "FREELANCER")</li>
 * </ul>
 * </p>
 */
@Getter
@Setter
public class UserDto {

    /**
     * 사용자 고유 식별자 (User 엔티티의 ID)
     */
    private Long id;

    /**
     * 로그인에 사용되는 사용자명
     */
    private String username;

    /**
     * 부여된 권한 정보
     * - "ADMIN"      : 관리자
     * - "EMPLOYER"   : 기업 사용자
     * - "FREELANCER" : 프리랜서 사용자
     */
    private String role;

    /**
     * 비즈니스 로직 구분용 사용자 유형
     * - "EMPLOYER"   : 기업 사용자
     * - "FREELANCER" : 프리랜서 사용자
     */
    private String userType;

    /**
     * 모든 필드를 인자로 받는 생성자.
     *
     * @param id        사용자 고유 식별자
     * @param username  로그인에 사용되는 아이디
     * @param role      부여된 권한
     * @param userType  사용자 유형
     */
    public UserDto(Long id, String username, String role, String userType) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.userType = userType;
    }
}
