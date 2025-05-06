package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.Profile;
import com.example.yellowaution.service.AccountRecoveryService;
import com.example.yellowaution.repository.ProfileRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * AccountRecoveryServiceImpl 클래스는 사용자의 아이디(Username) 찾기 기능을
 * 구현한 서비스 클래스입니다.
 *
 * <p>주요 역할:
 * <ul>
 *   <li>이메일을 기반으로 Profile 엔티티 조회</li>
 *   <li>해당 프로필에 연관된 User 엔티티에서 아이디(username)를 추출</li>
 *   <li>SimpleMailMessage를 사용해 이메일로 아이디 전송</li>
 * </ul>
 * </p>
 */
@Service
public class AccountRecoveryServiceImpl implements AccountRecoveryService {

    /**
     * ProfileRepository는 이메일로 Profile 조회 메서드(findByEmail)를 제공합니다.
     */
    private final ProfileRepository profileRepository;

    /**
     * JavaMailSender는 이메일 전송 기능을 제공하는 스프링 빈입니다.
     */
    private final JavaMailSender mailSender;

    /**
     * 생성자 주입(Constructor Injection)을 통해 의존 객체를 할당합니다.
     *
     * @param profileRepository 프로필 조회를 위한 JPA 리포지토리
     * @param mailSender        이메일 전송을 위한 JavaMailSender 빈
     */
    public AccountRecoveryServiceImpl(ProfileRepository profileRepository,
                                      JavaMailSender mailSender) {
        this.profileRepository = profileRepository;
        this.mailSender = mailSender;
    }

    /**
     * 사용자가 입력한 이메일 주소로 아이디(Username)를 발송합니다.
     *
     * <p>동작 순서:
     * <ol>
     *   <li>ProfileRepository.findByEmail(email) 호출하여 프로필 조회
     *       - 조회 실패 시 IllegalArgumentException 발생</li>
     *   <li>Profile 엔티티에서 연결된 User 엔티티의 username 값을 획득</li>
     *   <li>SimpleMailMessage 객체 생성 및 발송 정보 설정
     *       <ul>
     *         <li>발신자(from): yourId@forhim.kr 형식으로 설정</li>
     *         <li>수신자(to): 메서드 파라미터로 받은 이메일</li>
     *         <li>제목(subject): "[YellowAuction] 아이디 안내"</li>
     *         <li>본문(text): 사용자에게 안내할 아이디 포함</li>
     *       </ul>
     *   </li>
     *   <li>mailSender.send(msg) 호출하여 이메일 전송</li>
     * </ol>
     * </p>
     *
     * @param email 아이디 안내를 요청한 사용자의 이메일 주소
     * @throws IllegalArgumentException 등록되지 않은 이메일인 경우 예외 발생
     */
    @Override
    public void sendUsernameByEmail(String email) {
        // 1) 이메일로 Profile 조회
        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("등록된 이메일이 아닙니다."));

        // 2) Profile에 연관된 User 엔티티의 username 추출
        String username = profile.getUser().getUsername();

        // 3) 이메일 전송용 메시지 구성
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("william7872ksh@forhim.kr");  // 발신자 주소 (메일 서버 설정에 맞춰야 함)
        msg.setTo(email);                          // 수신자 이메일
        msg.setSubject("[YellowAuction] 아이디 안내"); // 이메일 제목
        msg.setText(
                "안녕하세요!\n\n" +
                        "요청하신 아이디는 다음과 같습니다:\n\n" +
                        "아이디: " + username + "\n"
        );

        // 4) 이메일 전송 실행
        mailSender.send(msg);
    }
}
