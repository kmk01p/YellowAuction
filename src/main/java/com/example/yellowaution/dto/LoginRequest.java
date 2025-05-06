package com.example.yellowaution.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * LoginRequest 클래스는 클라이언트가 로그인 요청 시
 * 전달하는 사용자 인증 정보를 담는 DTO입니다.
 *
 * <p>주요 역할:
 * <ul>
 *   <li>JSON 바디로 전달된 username, password 값을 매핑</li>
 *   <li>AuthRestController.login() 핸들러에서 파라미터로 사용</li>
 *   <li>서비스 레이어에서 해당 정보를 바탕으로 사용자 인증 수행</li>
 * </ul>
 * </p>
 *
 * <p>유효성 검증:
 * - username과 password가 비어 있지 않은지(@NotBlank 등)는
 *   컨트롤러 또는 서비스 레이어에서 별도로 어노테이션 또는 코드로 검증할 수 있습니다.</p>
 */
@Getter
@Setter
public class LoginRequest {

    /**
     * 로그인 ID (username)
     * - 클라이언트가 입력한 사용자 고유 아이디
     * - 서비스 레이어에서 해당 아이디로 사용자 조회 후 비밀번호 검증 진행
     */
    private String username;

    /**
     * 로그인 비밀번호 (password)
     * - 클라이언트가 입력한 평문 비밀번호
     * - BCryptPasswordEncoder.matches() 등을 통해 암호화된 비밀번호와 비교
     */
    private String password;
}
