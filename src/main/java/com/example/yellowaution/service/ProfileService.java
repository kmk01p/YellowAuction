package com.example.yellowaution.service;


import com.example.yellowaution.dto.ProfileDto;
import com.example.yellowaution.security.UserPrincipal;

import java.util.List;

public interface ProfileService {
    ProfileDto create(ProfileDto dto, UserPrincipal principal);

    ProfileDto update(Long id, ProfileDto dto, UserPrincipal principal);

    void delete(Long id, UserPrincipal principal);

    ProfileDto get(Long id, UserPrincipal principal);

    List<ProfileDto> getList(UserPrincipal principal);

    ProfileDto create(ProfileDto dto);
    ProfileDto update(Long id, ProfileDto dto);
    void delete(Long id);
    ProfileDto get(Long id);
    List<ProfileDto> getList();
    List<ProfileDto> getListByUserId(Long userId);
}
