package com.example.yellowaution.service;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.LoginRequestDto;
import com.example.yellowaution.dto.SignupRequestDto;
import com.example.yellowaution.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(LoginRequestDto dto) {
        return userRepository.findByUsername(dto.getUsername())
                .filter(user -> user.getPassword().equals(dto.getPassword()))
                .orElse(null);
    }

    public User signup(SignupRequestDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return userRepository.save(user);
    }
}