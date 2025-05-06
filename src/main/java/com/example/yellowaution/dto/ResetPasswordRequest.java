package com.example.yellowaution.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * ResetPasswordRequest 클래스는 비밀번호 재설정 요청 시
 * 클라이언트가 전달하는 데이터를 담는 DTO입니다.
 *
 * <p>유효성 검증:
 * <ul>
 *   <li>token: 비밀번호 재설정을 위한 검증 토큰 (NotBlank)</li>
 *   <li>newPassword: 새 비밀번호 (NotBlank)</li>
 * </ul>
 * </p>
 */
public class ResetPasswordRequest {

    /**
     * 비밀번호 재설정을 위해 이메일로 발송된 검증 토큰
     * - @NotBlank: null 또는 빈 문자열을 허용하지 않음
     */
    @NotBlank(message = "토큰은 필수 입력입니다.")
    private String token;

    /**
     * 사용자가 새로 설정할 비밀번호
     * - 서비스 레이어나 별도 검증 로직에서 비밀번호 복잡도 규칙(길이, 특수문자 등) 추가 검증 가능
     * - @NotBlank: null 또는 빈 문자열을 허용하지 않음
     */
    @NotBlank(message = "새 비밀번호는 필수 입력입니다.")
    private String newPassword;

    /**
     * 기본 생성자.
     * Jackson이나 Spring MVC의 @RequestBody 바인딩 시 내부적으로 사용됩니다.
     */
    public ResetPasswordRequest() {}

    /**
     * 검증 토큰을 반환합니다.
     *
     * @return 검증 토큰 문자열
     */
    public String getToken() {
        return token;
    }

    /**
     * 검증 토큰을 설정합니다.
     *
     * @param token 검증 토큰 문자열
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 새 비밀번호를 반환합니다.
     *
     * @return 새 비밀번호 문자열
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * 새 비밀번호를 설정합니다.
     *
     * @param newPassword 새 비밀번호 문자열
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
