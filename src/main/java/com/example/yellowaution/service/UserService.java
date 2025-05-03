package com.example.yellowaution.service;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.UserRegisterDto;

public interface UserService {
    void register(UserRegisterDto dto);
    User login(String username, String password);
}
