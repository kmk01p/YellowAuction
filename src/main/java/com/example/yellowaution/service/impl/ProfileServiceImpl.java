package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.Profile;
import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.ProfileDto;
import com.example.yellowaution.repository.ProfileRepository;
import com.example.yellowaution.repository.UserRepository;
import com.example.yellowaution.security.UserPrincipal;
import com.example.yellowaution.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository repository;
    private final UserRepository userRepository;

    private ProfileDto toDto(Profile e) {
        ProfileDto d = new ProfileDto();
        d.setId(e.getId());
        d.setCompanyName(e.getCompanyName());
        d.setRepresentative(e.getRepresentative());
        d.setCompanySize(e.getCompanySize());
        d.setEstablishedDate(e.getEstablishedDate());
        d.setMainIndustry(e.getMainIndustry());
        d.setAddress(e.getAddress());
        d.setEmployees(e.getEmployees());
        d.setCapital(e.getCapital());
        d.setAnnualRevenue(e.getAnnualRevenue());
        d.setHomepageUrl(e.getHomepageUrl());
        d.setPhone(e.getPhone());
        d.setEmail(e.getEmail());
        d.setUserId(e.getUser().getId());
        return d;
    }

    private Profile toEntity(ProfileDto d, User user) {
        Profile e = new Profile();
        e.setCompanyName(d.getCompanyName());
        e.setRepresentative(d.getRepresentative());
        e.setCompanySize(d.getCompanySize());
        e.setEstablishedDate(d.getEstablishedDate());
        e.setMainIndustry(d.getMainIndustry());
        e.setAddress(d.getAddress());
        e.setEmployees(d.getEmployees());
        e.setCapital(d.getCapital());
        e.setAnnualRevenue(d.getAnnualRevenue());
        e.setHomepageUrl(d.getHomepageUrl());
        e.setPhone(d.getPhone());
        e.setEmail(d.getEmail());
        e.setUser(user);
        return e;
    }

    @Override
    public ProfileDto create(ProfileDto dto, UserPrincipal principal) {
        User user = userRepository.findById(principal.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        Profile saved = repository.save(toEntity(dto, user));
        return toDto(saved);
    }

    @Override
    public ProfileDto update(Long id, ProfileDto dto, UserPrincipal principal) {
        Profile e = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프로필이 없습니다. id=" + id));

        if (!e.getUser().getId().equals(principal.getUser().getId())) {
            throw new AccessDeniedException("접근 권한 없음");
        }

        e.setCompanyName(dto.getCompanyName());
        e.setRepresentative(dto.getRepresentative());
        e.setCompanySize(dto.getCompanySize());
        e.setEstablishedDate(dto.getEstablishedDate());
        e.setMainIndustry(dto.getMainIndustry());
        e.setAddress(dto.getAddress());
        e.setEmployees(dto.getEmployees());
        e.setCapital(dto.getCapital());
        e.setAnnualRevenue(dto.getAnnualRevenue());
        e.setHomepageUrl(dto.getHomepageUrl());
        e.setPhone(dto.getPhone());
        e.setEmail(dto.getEmail());

        return toDto(repository.save(e));
    }

    @Override
    public void delete(Long id, UserPrincipal principal) {
        Profile e = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프로필이 없습니다. id=" + id));

        if (!e.getUser().getId().equals(principal.getUser().getId())) {
            throw new AccessDeniedException("삭제 권한 없음");
        }

        repository.deleteById(id);
    }

    @Override
    public ProfileDto get(Long id, UserPrincipal principal) {
        Profile e = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프로필이 없습니다. id=" + id));

        if (!e.getUser().getId().equals(principal.getUser().getId())) {
            throw new AccessDeniedException("조회 권한 없음");
        }

        return toDto(e);
    }

    @Override
    public List<ProfileDto> getList(UserPrincipal principal) {
        return repository.findAll().stream()
                .filter(e -> e.getUser().getId().equals(principal.getUser().getId()))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProfileDto create(ProfileDto dto) {
        return null;
    }

    @Override
    public ProfileDto update(Long id, ProfileDto dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public ProfileDto get(Long id) {
        return null;
    }

    @Override
    public List<ProfileDto> getList() {
        return List.of();
    }

    @Override
    public List<ProfileDto> getListByUserId(Long userId) {
        return List.of();
    }
}
