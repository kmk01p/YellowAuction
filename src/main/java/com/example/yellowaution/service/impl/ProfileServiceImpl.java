package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.Profile;
import com.example.yellowaution.dto.ProfileDto;
import com.example.yellowaution.repository.ProfileRepository;
import com.example.yellowaution.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository repository;

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
        return d;
    }

    private Profile toEntity(ProfileDto d) {
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
        return e;
    }

    @Override
    public ProfileDto create(ProfileDto dto) {
        Profile saved = repository.save(toEntity(dto));
        return toDto(saved);
    }

    @Override
    public ProfileDto update(Long id, ProfileDto dto) {
        Profile e = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프로필이 없습니다. id=" + id));
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
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ProfileDto get(Long id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("프로필이 없습니다. id=" + id));
    }

    @Override
    public List<ProfileDto> getList() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
