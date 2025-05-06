package com.example.yellowaution.repository;


import com.example.yellowaution.domain.PasswordResetToken;
import com.example.yellowaution.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    // 같은 유저에 대해 기존 코드 모두 삭제
    void deleteAllByUser(User user);
}
