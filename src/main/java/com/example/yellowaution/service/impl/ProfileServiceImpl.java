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

/**
 * ProfileServiceImpl 클래스는 ProfileService 인터페이스를 구현하여
 * 프로필 생성·조회·수정·삭제 기능을 제공하는 서비스 레이어입니다.
 *
 * <ul>
 *   <li>ProfileDto ↔ Profile 엔티티 변환 헬퍼 메서드 제공</li>
 *   <li>인증된 사용자(UserPrincipal) 기반 권한 검증 수행</li>
 *   <li>소유자만 프로필 수정/삭제/조회 가능하도록 AccessDeniedException 처리</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor  // final 필드에 대해 생성자 자동 주입
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository repository;  // 프로필 데이터 접근 JPA 리포지토리
    private final UserRepository userRepository; // 사용자 조회용 JPA 리포지토리

    /**
     * Profile 엔티티를 ProfileDto로 변환하는 헬퍼 메서드
     *
     * @param e Profile 엔티티
     * @return ProfileDto 변환 결과
     */
    private ProfileDto toDto(Profile e) {
        ProfileDto d = new ProfileDto();
        d.setId(e.getId());
        // ─── 공통 필드 매핑 ─────────────────────────
        d.setName(e.getName());
        d.setPhone(e.getPhone());
        d.setEmail(e.getEmail());
        // ─── 기업 전용 필드 매핑 ───────────────────────
        d.setRepresentative(e.getRepresentative());
        d.setCompanySize(e.getCompanySize());
        d.setEstablishedDate(e.getEstablishedDate());
        d.setMainIndustry(e.getMainIndustry());
        d.setAddress(e.getAddress());
        d.setEmployees(e.getEmployees());
        d.setCapital(e.getCapital());
        d.setAnnualRevenue(e.getAnnualRevenue());
        d.setHomepageUrl(e.getHomepageUrl());
        // ─── 프리랜서 전용 필드 매핑 ───────────────────
        d.setJobType(e.getJobType());
        d.setCareer(e.getCareer());
        d.setTechStack(e.getTechStack());
        // 연관된 User ID 설정
        d.setUserId(e.getUser().getId());
        return d;
    }

    /**
     * ProfileDto와 User 엔티티를 결합해 Profile 엔티티로 변환하는 헬퍼 메서드
     *
     * @param d ProfileDto
     * @param user 소유자 User 엔티티
     * @return Profile 엔티티
     */
    private Profile toEntity(ProfileDto d, User user) {
        Profile e = new Profile();
        // ─── 공통 필드 매핑 ─────────────────────────
        e.setName(d.getName());
        e.setPhone(d.getPhone());
        e.setEmail(d.getEmail());
        // ─── 기업 전용 필드 매핑 ───────────────────────
        e.setRepresentative(d.getRepresentative());
        e.setCompanySize(d.getCompanySize());
        e.setEstablishedDate(d.getEstablishedDate());
        e.setMainIndustry(d.getMainIndustry());
        e.setAddress(d.getAddress());
        e.setEmployees(d.getEmployees());
        e.setCapital(d.getCapital());
        e.setAnnualRevenue(d.getAnnualRevenue());
        e.setHomepageUrl(d.getHomepageUrl());
        // ─── 프리랜서 전용 필드 매핑 ───────────────────
        e.setJobType(d.getJobType());
        e.setCareer(d.getCareer());
        e.setTechStack(d.getTechStack());
        // 소유자 설정
        e.setUser(user);
        return e;
    }

    /**
     * 새로운 프로필을 생성합니다.
     *
     * @param dto ProfileDto (생성할 프로필 정보)
     * @param principal 인증된 사용자 정보(UserPrincipal)
     * @return 생성된 ProfileDto
     * @throws IllegalArgumentException 사용자가 존재하지 않을 경우
     */
    @Override
    public ProfileDto create(ProfileDto dto, UserPrincipal principal) {
        // UserPrincipal에서 사용자 ID 추출 후 DB에서 사용자 조회
        User user = userRepository.findById(principal.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        // ProfileDto → Profile 엔티티 변환 후 저장
        Profile saved = repository.save(toEntity(dto, user));
        // 저장된 엔티티를 DTO로 변환하여 반환
        return toDto(saved);
    }

    /**
     * 기존 프로필을 수정합니다.
     *
     * @param id 수정할 프로필 ID
     * @param dto 업데이트할 ProfileDto
     * @param principal 인증된 사용자 정보(UserPrincipal)
     * @return 수정된 ProfileDto
     * @throws IllegalArgumentException 프로필이 없거나
     * @throws AccessDeniedException 소유자가 아닐 경우
     */
    @Override
    public ProfileDto update(Long id, ProfileDto dto, UserPrincipal principal) {
        // 프로필 조회
        Profile e = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프로필이 없습니다. id=" + id));
        // 권한 검증: 소유자만 수정 가능
        if (!e.getUser().getId().equals(principal.getUser().getId())) {
            throw new AccessDeniedException("접근 권한 없음");
        }
        // ─── 공통 필드 업데이트 ───────────────────────
        e.setName(dto.getName());
        e.setPhone(dto.getPhone());
        e.setEmail(dto.getEmail());
        // ─── 기업 전용 필드 업데이트 ───────────────────
        e.setRepresentative(dto.getRepresentative());
        e.setCompanySize(dto.getCompanySize());
        e.setEstablishedDate(dto.getEstablishedDate());
        e.setMainIndustry(dto.getMainIndustry());
        e.setAddress(dto.getAddress());
        e.setEmployees(dto.getEmployees());
        e.setCapital(dto.getCapital());
        e.setAnnualRevenue(dto.getAnnualRevenue());
        e.setHomepageUrl(dto.getHomepageUrl());
        // ─── 프리랜서 전용 필드 업데이트 ───────────────
        e.setJobType(dto.getJobType());
        e.setCareer(dto.getCareer());
        e.setTechStack(dto.getTechStack());
        // 수정된 엔티티 저장 및 DTO 변환
        return toDto(repository.save(e));
    }

    /**
     * 프로필을 삭제합니다.
     *
     * @param id 삭제할 프로필 ID
     * @param principal 인증된 사용자 정보(UserPrincipal)
     * @throws IllegalArgumentException 프로필이 없거나
     * @throws AccessDeniedException 소유자가 아닐 경우
     */
    @Override
    public void delete(Long id, UserPrincipal principal) {
        // 프로필 조회
        Profile e = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프로필이 없습니다. id=" + id));
        // 권한 검증: 소유자만 삭제 가능
        if (!e.getUser().getId().equals(principal.getUser().getId())) {
            throw new AccessDeniedException("삭제 권한 없음");
        }
        // 프로필 삭제
        repository.deleteById(id);
    }

    /**
     * 단일 프로필을 조회합니다.
     *
     * @param id 조회할 프로필 ID
     * @param principal 인증된 사용자 정보(UserPrincipal)
     * @return 조회된 ProfileDto
     * @throws IllegalArgumentException 프로필이 없거나
     * @throws AccessDeniedException 소유자가 아닐 경우
     */
    @Override
    public ProfileDto get(Long id, UserPrincipal principal) {
        // 프로필 조회
        Profile e = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프로필이 없습니다. id=" + id));
        // 권한 검증: 소유자만 조회 가능
        if (!e.getUser().getId().equals(principal.getUser().getId())) {
            throw new AccessDeniedException("조회 권한 없음");
        }
        // DTO 변환 후 반환
        return toDto(e);
    }

    /**
     * 인증된 사용자가 소유한 모든 프로필을 조회합니다.
     *
     * @param principal 인증된 사용자 정보(UserPrincipal)
     * @return ProfileDto 리스트
     */
    @Override
    public List<ProfileDto> getList(UserPrincipal principal) {
        // findAll() 후 필터로 소유자만 추출하여 DTO 변환
        return repository.findAll().stream()
                .filter(e -> e.getUser().getId().equals(principal.getUser().getId()))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ────────────────────────────────────────────────────────
    // ProfileService 인터페이스 확장 메서드 (인증 없는 호출 미지원)
    // ────────────────────────────────────────────────────────

    @Override
    public ProfileDto create(ProfileDto dto) {
        return null;
    }

    @Override
    public ProfileDto update(Long id, ProfileDto dto) {
        return null;
    }

    @Override
    public void delete(Long id) { }

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
