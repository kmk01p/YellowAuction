package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.Profile;
import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.ProfileDto;
import com.example.yellowaution.dto.UserRegisterDto;
import com.example.yellowaution.repository.ProfileRepository;
import com.example.yellowaution.repository.UserRepository;
import com.example.yellowaution.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           ProfileRepository profileRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void register(UserRegisterDto dto) {
        userRepository.findByUsername(dto.getUsername()).ifPresent(u -> {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        });

        User user = new User(dto.getUsername(), passwordEncoder.encode(dto.getPassword()), dto.getRole(), dto.getUserType());
        userRepository.save(user);

        Profile profile = toProfileEntity(dto.getProfile());
        profile.setUser(user);  // 👈 유저 연관관계 설정
        profileRepository.save(profile);
    }

    @Override
    public User login(String username, String password) {
        return null;
    }

    private Profile toProfileEntity(ProfileDto d) {
        Profile e = new Profile();
        e.setName(d.getName());
        e.setPhone(d.getPhone());
        e.setEmail(d.getEmail());

        e.setRepresentative(d.getRepresentative());
        e.setCompanySize(d.getCompanySize());
        e.setEstablishedDate(d.getEstablishedDate());
        e.setMainIndustry(d.getMainIndustry());
        e.setAddress(d.getAddress());
        e.setEmployees(d.getEmployees());
        e.setCapital(d.getCapital());
        e.setAnnualRevenue(d.getAnnualRevenue());
        e.setHomepageUrl(d.getHomepageUrl());

        e.setJobType(d.getJobType());
        e.setCareer(d.getCareer());
        e.setTechStack(d.getTechStack());
        return e;
    }
}
