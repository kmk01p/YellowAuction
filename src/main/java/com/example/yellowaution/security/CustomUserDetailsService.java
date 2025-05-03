package com.example.yellowaution.security;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserRepository userRepository,
                                    BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DB에서 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // UserPrincipal은 UserDetails 구현체
        return new UserPrincipal(user);
    }

    // 추가적으로 회원가입 시 비밀번호 암호화가 필요한 경우
    public void registerUser(String username, String rawPassword, String role, String userType) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);  // ✅ 암호화
        User user = new User(username, encodedPassword, role, userType);
        userRepository.save(user);
    }
}
