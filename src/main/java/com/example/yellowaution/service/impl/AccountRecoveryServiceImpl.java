package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.Profile;
import com.example.yellowaution.service.AccountRecoveryService;
import com.example.yellowaution.repository.ProfileRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AccountRecoveryServiceImpl implements AccountRecoveryService {

    private final ProfileRepository profileRepository;
    private final JavaMailSender mailSender;

    public AccountRecoveryServiceImpl(ProfileRepository profileRepository,
                                      JavaMailSender mailSender) {
        this.profileRepository = profileRepository;
        this.mailSender = mailSender;
    }

    @Override
    public void sendUsernameByEmail(String email) {
        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("등록된 이메일이 아닙니다."));

        String username = profile.getUser().getUsername();

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("william7872ksh@forhim.kr");  // ← 반드시 yourId@forhim.kr 과 동일하
        msg.setTo(email);
        msg.setSubject("[YellowAuction] 아이디 안내");
        msg.setText("안녕하세요!\n\n요청하신 아이디는 다음과 같습니다:\n\n"
                + "아이디: " + username);
        mailSender.send(msg);
    }
}
