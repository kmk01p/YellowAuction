package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.PasswordResetToken;
import com.example.yellowaution.domain.Profile;
import com.example.yellowaution.domain.User;
import com.example.yellowaution.repository.PasswordResetTokenRepository;
import com.example.yellowaution.repository.ProfileRepository;
import com.example.yellowaution.repository.UserRepository;
import com.example.yellowaution.service.PasswordResetService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private final ProfileRepository profileRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetServiceImpl(ProfileRepository profileRepository,
                                    PasswordResetTokenRepository tokenRepository,
                                    UserRepository userRepository,
                                    JavaMailSender mailSender,
                                    PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void createAndSendResetToken(String email) {
        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("등록된 이메일이 아닙니다."));
        User user = profile.getUser();

        String token = UUID.randomUUID().toString();
        PasswordResetToken prt = new PasswordResetToken();
        prt.setToken(token);
        prt.setExpiry(LocalDateTime.now().plusHours(1));
        prt.setUser(user);
        tokenRepository.save(prt);

        String resetUrl = "https://forhim.kr/api/recover/password?token=" + token;
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("william7872ksh@forhim.kr");  // ← 반드시 yourId@forhim.kr 과 동일하게
        msg.setTo(email);
        msg.setSubject("[YellowAuction] 비밀번호 재설정 링크");
        msg.setText("아래 링크를 클릭해 비밀번호를 재설정하세요. (1시간 유효)\n\n" + resetUrl);
        mailSender.send(msg);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken prt = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));
        if (prt.getExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("토큰이 만료되었습니다.");
        }

        User user = prt.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        tokenRepository.delete(prt);
    }
}
