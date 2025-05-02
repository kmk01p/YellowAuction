package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.repository.UserRepository;
import com.example.yellowaution.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(String username, String password, String userType) {
        userRepository.findByUsername(username).ifPresent(u -> {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        });

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("USER");
        user.setUserType(userType);
        userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return user;
    }
}
