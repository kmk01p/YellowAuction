package com.example.yellowaution.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * UserRegisterDto 클래스는 회원가입(Registration) 요청 시
 * 클라이언트로부터 전달되는 모든 정보를 담는 DTO(Data Transfer Object)입니다.
 *
 * <p>주요 역할:
 * <ul>
 *   <li>username, password, role, userType 등의 계정 설정 정보 수집</li>
 *   <li>profile 필드를 통해 기업 또는 프리랜서 프로필 정보 동시 전송 지원</li>
 *   <li>AuthRestController 및 UserService에서 회원가입 로직 처리 시 활용</li>
 * </ul>
 * </p>
 *
 * <p>사용 흐름:
 * <ol>
 *   <li>클라이언트가 JSON 형태로 이 DTO를 전송</li>
 *   <li>컨트롤러에서 @RequestBody로 바인딩</li>
 *   <li>UserService.register(dto) 호출 → 유효성 검사, 중복 체크</li>
 *   <li>비밀번호 암호화 후 User 엔티티 및 Profile 엔티티 생성 → DB 저장</li>
 * </ol>
 * </p>
 */
@Getter
@Setter
public class UserRegisterDto {

    /**
     * 로그인에 사용할 고유 아이디
     * - 반드시 중복되지 않아야 하며, 영문·숫자 조합 등 정책에 맞춰 유효성 검사 필요
     */
    private String username;

    /**
     * 사용자 비밀번호(평문)
     * - 서비스 레이어에서 BCryptPasswordEncoder 등을 이용해 해시 처리 후 저장
     * - 최소 길이, 특수문자 포함 여부 등 추가 검증 로직 적용 권장
     */
    private String password;

    /**
     * Spring Security 인가(Authorization) 시 사용되는 권한 정보
     * - 예: "ROLE_ADMIN", "ROLE_EMPLOYER", "ROLE_FREELANCER"
     * - 앞에 "ROLE_" 접두사 사용 여부는 SecurityConfig에 맞춰 설정
     */
    private String role;

    /**
     * 애플리케이션 비즈니스 로직에서 사용자 유형 구분용 필드
     * - "EMPLOYER" 또는 "FREELANCER"
     * - 대시보드, 프로필, 기능 노출 여부 분기를 위해 사용
     */
    private String userType;

    /**
     * 회원가입 시 함께 제출할 프로필 정보
     * - 기업 사용자의 경우 회사명, 업종, 직원 수 등 ProfileDto의 기업 전용 필드 활용
     * - 프리랜서 사용자의 경우 직무, 경력, 기술 스택 등 ProfileDto의 프리랜서 전용 필드 활용
     * - ProfileDto 내부 유효성 검증(@NotBlank, @Email 등) 적용 여부 확인 필요
     */
    private ProfileDto profile;
}
