package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.repository.UserRepository;
import com.example.yellowaution.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(String username, String password, String userType) {
        userRepository.findByUsername(username).ifPresent(u -> {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        });

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // 🔐 암호화된 비밀번호 저장
        user.setRole("USER"); // 기본 권한
        user.setUserType(userType); // EMPLOYER or FREELANCER
        userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        // 더 이상 사용되지 않지만, REST 로그인이나 테스트 용도로 유지 가능
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        return user;
    }
}
