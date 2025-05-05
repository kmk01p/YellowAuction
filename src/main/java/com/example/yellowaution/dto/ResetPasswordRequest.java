package com.example.yellowaution.dto;



import jakarta.validation.constraints.NotBlank;

public class ResetPasswordRequest {

    @NotBlank(message = "토큰은 필수 입력입니다.")
    private String token;

    @NotBlank(message = "새 비밀번호는 필수 입력입니다.")
    private String newPassword;

    public ResetPasswordRequest() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
