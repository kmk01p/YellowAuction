package com.example.yellowaution.service;

import com.example.yellowaution.domain.User;

public interface UserService {
    void register(String username, String password, String role);
    User login(String username, String password);
}
