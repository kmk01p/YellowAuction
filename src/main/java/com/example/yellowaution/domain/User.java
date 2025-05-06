package com.example.yellowaution.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User 엔티티는 애플리케이션에 로그인 가능한 사용자 계정 정보를 저장합니다.
 *
 * <p>주요 역할:
 * <ul>
 *   <li>로그인 ID(username)와 암호화된 비밀번호(password) 관리</li>
 *   <li>사용자 권한(role) 부여 (예: ADMIN, EMPLOYER, FREELANCER)</li>
 *   <li>사용자 타입(userType) 구분 (기업 또는 프리랜서)</li>
 * </ul>
 * </p>
 *
 * <p>JPA를 통해 데이터베이스 테이블과 매핑되며, 기본 생성자가 필수입니다.</p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor  // JPA가 리플렉션으로 엔티티를 생성할 때 사용
public class User {

    /**
     * 사용자 고유 식별자(ID)
     * - @Id: 기본 키 설정
     * - @GeneratedValue(strategy = GenerationType.IDENTITY):
     *   데이터베이스의 AUTO_INCREMENT 컬럼 사용
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 로그인에 사용할 고유한 사용자명(username)
     * - 회원가입 시 중복 체크를 통해 고유 보장
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * 암호화된 비밀번호(password)
     * - BCryptPasswordEncoder 등을 사용해 해시된 값 저장
     * - 평문(password) 절대 저장하지 않음
     */
    @Column(nullable = false)
    private String password;

    /**
     * 사용자 권한(role)
     * - Spring Security의 GrantedAuthority로 활용 (예: "ADMIN", "EMPLOYER", "FREELANCER")
     * - 앞에 "ROLE_" 접두사를 붙여 관리할 수도 있음
     */
    @Column(nullable = false)
    private String role;

    /**
     * 사용자 유형(userType)
     * - 비즈니스 로직에서 기업(EMPLOYER) 또는 프리랜서(FREELANCER)를 구분하는 용도
     * - 권한(role)과 별도로 프로필, 대시보드 분기 등에 사용
     */
    @Column(nullable = false)
    private String userType;

    /**
     * 회원가입 시 사용하는 생성자
     *
     * @param username  로그인 ID
     * @param password  암호화된 비밀번호
     * @param role      부여할 권한
     * @param userType  사용자 유형 (기업/프리랜서)
     */
    public User(String username, String password, String role, String userType) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.userType = userType;
    }
}
