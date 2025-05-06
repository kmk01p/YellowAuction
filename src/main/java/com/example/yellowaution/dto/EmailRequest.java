package com.example.yellowaution.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * EmailRequest 클래스는 계정 복구(아이디 찾기) 시 클라이언트로부터
 * 전달받은 이메일 주소를 검증하고 저장하는 DTO입니다.
 *
 * <p>유효성 검증:
 * <ul>
 *   <li>@Email: RFC 표준에 맞는 이메일 형식인지 확인</li>
 *   <li>@NotBlank: null 또는 빈 문자열이 아닌지 확인</li>
 * </ul>
 * </p>
 */
public class EmailRequest {

    /**
     * 사용자에게 입력받은 이메일 주소
     * - 필수 입력 사항이며 비어 있을 수 없습니다.
     * - 올바른 이메일 형식이어야 합니다.
     */
    @Email(message = "유효한 이메일 주소여야 합니다.")
    @NotBlank(message = "이메일은 필수 입력입니다.")
    private String email;

    /**
     * 기본 생성자.
     * Jackson이나 Spring에서 @RequestBody 바인딩 시 내부적으로 사용됩니다.
     */
    public EmailRequest() {}

    /**
     * 이메일 주소를 반환합니다.
     *
     * @return 입력된 이메일 문자열
     */
    public String getEmail() {
        return email;
    }

    /**
     * 이메일 주소를 설정합니다.
     *
     * @param email 설정할 이메일 주소 (유효성 검증은 애노테이션에 따라 수행됨)
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
