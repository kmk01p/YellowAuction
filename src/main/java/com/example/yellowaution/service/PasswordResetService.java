package com.example.yellowaution.service;



public interface PasswordResetService {
    void sendVerificationCode(String username, String email);

    void verifyCode(String username, String email, String code);

    void resetPassword(String code, String newPassword);
}


