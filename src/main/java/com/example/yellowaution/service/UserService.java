package com.example.yellowaution.service;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.UserAdminDto;
import com.example.yellowaution.dto.UserDto;
import com.example.yellowaution.dto.UserRegisterDto;

import java.util.List;

public interface UserService {
    void register(UserRegisterDto dto);
    User login(String username, String password);
    List<UserAdminDto> findAllForAdmin();

}
