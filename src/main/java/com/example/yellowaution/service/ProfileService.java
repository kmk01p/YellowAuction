package com.example.yellowaution.service;


import com.example.yellowaution.dto.ProfileDto;

import java.util.List;

public interface ProfileService {
    ProfileDto create(ProfileDto dto);
    ProfileDto update(Long id, ProfileDto dto);
    void delete(Long id);
    ProfileDto get(Long id);
    List<ProfileDto> getList();
}
