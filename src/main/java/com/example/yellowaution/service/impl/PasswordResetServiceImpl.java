// src/main/java/com/example/yellowaution/service/impl/PasswordResetServiceImpl.java
package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.PasswordResetToken;
import com.example.yellowaution.domain.Profile;
import com.example.yellowaution.domain.User;
import com.example.yellowaution.repository.PasswordResetTokenRepository;
import com.example.yellowaution.repository.ProfileRepository;
import com.example.yellowaution.service.PasswordResetService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private final ProfileRepository profileRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetServiceImpl(ProfileRepository profileRepository,
                                    PasswordResetTokenRepository tokenRepository,
                                    JavaMailSender mailSender,
                                    PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    /** 1) 아이디+이메일 검증 후 6자리 인증번호 발송 */
    @Override
    @Transactional
    public void sendVerificationCode(String username, String email) {
        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("등록된 이메일이 아닙니다."));
        User user = profile.getUser();
        if (!user.getUsername().equals(username)) {
            throw new IllegalArgumentException("아이디와 이메일이 일치하지 않습니다.");
        }

        // 기존 코드 삭제
        tokenRepository.deleteAllByUser(user);

        // 6자리 숫자 생성
        String code = String.format("%06d", new Random().nextInt(1_000_000));
        PasswordResetToken prt = new PasswordResetToken();
        prt.setToken(code);
        prt.setExpiry(LocalDateTime.now().plusMinutes(10));
        prt.setUser(user);
        tokenRepository.save(prt);

        // 이메일 발송
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("william7872ksh@forhim.kr");
        msg.setTo(email);
        msg.setSubject("[YellowAuction] 비밀번호 찾기 인증번호");
        msg.setText("인증번호: " + code + " (10분 이내 유효)");
        mailSender.send(msg);
    }

    /** 2) 인증번호와 유저 매칭 검증 */
    @Override
    @Transactional(readOnly = true)
    public void verifyCode(String username, String email, String code) {
        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("등록된 이메일이 아닙니다."));
        User user = profile.getUser();
        if (!user.getUsername().equals(username)) {
            throw new IllegalArgumentException("아이디와 이메일이 일치하지 않습니다.");
        }

        PasswordResetToken prt = tokenRepository.findByToken(code)
                .orElseThrow(() -> new IllegalArgumentException("인증번호가 올바르지 않습니다."));
        if (!prt.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("인증번호가 올바르지 않습니다.");
        }
        if (prt.getExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("인증번호가 만료되었습니다.");
        }
    }

    /** 3) 인증된 code를 사용해 비밀번호 변경 */
    @Override
    @Transactional
    public void resetPassword(String code, String newPassword) {
        PasswordResetToken prt = tokenRepository.findByToken(code)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 인증번호입니다."));
        if (prt.getExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("인증번호가 만료되었습니다.");
        }

        User user = prt.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        // UserRepository.save(user) 은 User가 관리 상태이므로 생략 가능

        tokenRepository.delete(prt);
    }
}
