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
import java.util.Random;

/**
 * PasswordResetServiceImpl 클래스는 비밀번호 재설정(Recovery) 과정을 담당하는 서비스 구현체입니다.
 *
 * <p>주요 기능:
 * <ol>
 *   <li>사용자 아이디와 이메일 검증 후 6자리 인증번호 생성 및 이메일 발송</li>
 *   <li>발송된 인증번호와 사용자 매칭 및 유효기간 검증</li>
 *   <li>인증이 완료된 토큰을 사용해 실제 비밀번호를 암호화하여 저장</li>
 * </ol>
 * </p>
 *
 * <p>트랜잭션:
 * - sendVerificationCode, resetPassword 메서드는 쓰기 작업이므로 기본 @Transactional
 * - verifyCode 메서드는 읽기 전용이므로 @Transactional(readOnly = true)</p>
 */
@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private final ProfileRepository profileRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    /**
     * 생성자 주입을 통해 의존 객체를 초기화합니다.
     *
     * @param profileRepository 프로필 조회용 리포지토리
     * @param tokenRepository   비밀번호 재설정 토큰 저장소
     * @param mailSender        이메일 발송용 JavaMailSender
     * @param passwordEncoder   비밀번호 암호화 도구
     */
    public PasswordResetServiceImpl(ProfileRepository profileRepository,
                                    PasswordResetTokenRepository tokenRepository,
                                    JavaMailSender mailSender,
                                    PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 1) 인증번호 요청: 사용자 아이디(username)와 이메일(email)을 검증한 후,
     *    6자리 숫자 인증번호를 생성하여 이메일로 발송합니다.
     *
     * @param username 요청한 사용자 아이디
     * @param email    요청한 이메일 주소
     * @throws IllegalArgumentException 등록되지 않은 이메일이거나 아이디가 일치하지 않을 경우
     */
    @Override
    @Transactional
    public void sendVerificationCode(String username, String email) {
        // 1. 이메일로 프로필 조회
        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("등록된 이메일이 아닙니다."));
        User user = profile.getUser();

        // 2. 요청한 아이디와 이메일의 사용자 일치 여부 확인
        if (!user.getUsername().equals(username)) {
            throw new IllegalArgumentException("아이디와 이메일이 일치하지 않습니다.");
        }

        // 3. 기존에 발급된 모든 토큰 삭제 (하나의 사용자당 하나의 활성 토큰만 존재)
        tokenRepository.deleteAllByUser(user);

        // 4. 6자리 랜덤 숫자 인증번호 생성 (000000 ~ 999999)
        String code = String.format("%06d", new Random().nextInt(1_000_000));

        // 5. 토큰 엔티티 생성 및 만료시간 설정 (현재 시각 + 10분)
        PasswordResetToken prt = new PasswordResetToken();
        prt.setToken(code);
        prt.setExpiry(LocalDateTime.now().plusMinutes(10));
        prt.setUser(user);
        tokenRepository.save(prt);

        // 6. 이메일 메시지 구성 및 발송
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("william7872ksh@forhim.kr");  // 메일 서버 설정에 맞춰 발신자 주소 지정
        msg.setTo(email);
        msg.setSubject("[YellowAuction] 비밀번호 찾기 인증번호");
        msg.setText(
                "안녕하세요!\n\n" +
                        "비밀번호 재설정을 위한 인증번호는 다음과 같습니다:\n" +
                        code + " (10분 이내 유효)\n\n" +
                        "감사합니다."
        );
        mailSender.send(msg);
    }

    /**
     * 2) 인증번호 검증: 발송된 코드가 해당 사용자와 매칭되고, 만료되지 않았는지 확인합니다.
     *
     * @param username 요청한 사용자 아이디
     * @param email    요청한 이메일 주소
     * @param code     사용자가 입력한 인증번호
     * @throws IllegalArgumentException 잘못된 정보 или 만료된 코드일 경우
     */
    @Override
    @Transactional(readOnly = true)
    public void verifyCode(String username, String email, String code) {
        // 1. 이메일로 프로필 조회
        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("등록된 이메일이 아닙니다."));
        User user = profile.getUser();

        // 2. 아이디 불일치 시 예외
        if (!user.getUsername().equals(username)) {
            throw new IllegalArgumentException("아이디와 이메일이 일치하지 않습니다.");
        }

        // 3. 토큰 값으로 엔티티 조회
        PasswordResetToken prt = tokenRepository.findByToken(code)
                .orElseThrow(() -> new IllegalArgumentException("인증번호가 올바르지 않습니다."));

        // 4. 사용자 매칭 확인
        if (!prt.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("인증번호가 올바르지 않습니다.");
        }

        // 5. 만료시간 지났으면 예외
        if (prt.getExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("인증번호가 만료되었습니다.");
        }
    }

    /**
     * 3) 새 비밀번호 변경: 검증된 토큰을 사용해 사용자의 비밀번호를 암호화하여 업데이트합니다.
     *
     * @param code        검증된 인증번호(토큰)
     * @param newPassword 사용자가 새로 설정할 비밀번호(평문)
     * @throws IllegalArgumentException 유효하지 않거나 만료된 토큰일 경우
     */
    @Override
    @Transactional
    public void resetPassword(String code, String newPassword) {
        // 1. 토큰 조회
        PasswordResetToken prt = tokenRepository.findByToken(code)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 인증번호입니다."));

        // 2. 만료시간 확인
        if (prt.getExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("인증번호가 만료되었습니다.");
        }

        // 3. 사용자 엔티티 가져와 비밀번호 암호화 후 설정
        User user = prt.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));

        // 4. 토큰 삭제: 재사용 방지
        tokenRepository.delete(prt);
        // User 엔티티는 영속 상태이므로 별도 save 호출 없이 자동 반영
    }
}
