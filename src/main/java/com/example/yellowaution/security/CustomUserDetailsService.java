package com.example.yellowaution.security;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService 클래스는 Spring Security의 UserDetailsService를 구현하여
 * 인증(Authentication) 시 DB에서 사용자 정보를 조회하고 UserDetails 객체로 변환합니다.
 *
 * <p>주요 역할:
 * <ul>
 *   <li>loadUserByUsername(): 로그인 시 전달된 username으로 User 엔티티 조회</li>
 *   <li>UserPrincipal(UserDetails 구현체)을 생성하여 인증 컨텍스트에 전달</li>
 *   <li>registerUser(): 회원가입 로직 중 비밀번호 암호화 후 User 엔티티 저장 (필요 시 사용)</li>
 * </ul>
 * </p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /** 사용자 정보 조회를 위한 JPA 레포지토리 */
    private final UserRepository userRepository;

    /** 비밀번호 암호화를 위한 BCryptPasswordEncoder 빈 */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 생성자 주입(Constructor Injection)을 통해 Repository와 PasswordEncoder를 주입받습니다.
     *
     * @param userRepository   사용자 조회 및 저장을 위한 레포지토리
     * @param passwordEncoder  비밀번호 암호화 도구
     */
    public CustomUserDetailsService(UserRepository userRepository,
                                    BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * username을 기반으로 DB에서 사용자 정보를 조회하여 UserDetails 객체로 반환합니다.
     * Spring Security가 인증 처리 과정에서 호출합니다.
     *
     * @param username 로그인 시 입력된 사용자 아이디
     * @return UserDetails 인증에 사용될 사용자 정보 객체
     * @throws UsernameNotFoundException 사용자가 존재하지 않을 경우 발생
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DB에서 username으로 User 엔티티 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // UserPrincipal은 UserDetails를 구현한 클래스이며,
        // User 엔티티의 username, password, 권한(role) 정보를 포함
        return new UserPrincipal(user);
    }

    /**
     * 회원가입 처리 시 비밀번호를 암호화하여 User 엔티티로 저장하는 유틸 메서드입니다.
     * 필요 시 RegistrationService 등에서 호출하여 사용하세요.
     *
     * @param username    회원가입할 사용자 아이디
     * @param rawPassword 사용자가 입력한 평문 비밀번호
     * @param role        부여할 권한 (예: "ADMIN", "EMPLOYER", "FREELANCER")
     * @param userType    사용자 유형 (예: "EMPLOYER" 또는 "FREELANCER")
     * @throws IllegalArgumentException 이미 존재하는 username인 경우 발생
     */
    public void registerUser(String username, String rawPassword, String role, String userType) {
        // 중복된 사용자명(username) 체크
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        // 평문 비밀번호를 BCrypt 해시로 암호화
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 암호화된 비밀번호와 권한, 사용자 유형을 포함한 User 엔티티 생성
        User user = new User(username, encodedPassword, role, userType);

        // DB에 User 엔티티 저장
        userRepository.save(user);
    }
}
