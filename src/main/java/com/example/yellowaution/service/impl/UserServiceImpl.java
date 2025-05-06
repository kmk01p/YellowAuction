package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.Profile;
import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.ProfileDto;
import com.example.yellowaution.dto.UserAdminDto;
import com.example.yellowaution.dto.UserRegisterDto;
import com.example.yellowaution.repository.ProfileRepository;
import com.example.yellowaution.repository.UserRepository;
import com.example.yellowaution.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserServiceImpl 클래스는 UserService 인터페이스를 구현한 서비스 계층 클래스입니다.
 *
 * <ul>
 *   <li>관리자용 사용자 목록 조회(findAllForAdmin)</li>
 *   <li>회원가입(register) 처리</li>
 *   <li>로그인(login) 로직 (미구현)</li>
 * </ul>
 *
 * <p>트랜잭션 범위는 클래스 레벨 @Transactional로 설정되어 있으며,
 * 등록 및 프로필 저장 작업이 하나의 트랜잭션으로 묶여 안전하게 처리됩니다.</p>
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    /** 사용자 정보 조회 및 저장을 위한 JPA 레포지토리 */
    private final UserRepository userRepository;

    /** 프로필 정보 조회 및 저장을 위한 JPA 레포지토리 */
    private final ProfileRepository profileRepository;

    /** 비밀번호 암호화를 위한 BCryptPasswordEncoder 빈 */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 생성자 주입(Constructor Injection)을 통해 의존 객체를 초기화합니다.
     *
     * @param userRepository     사용자 엔티티 저장소
     * @param profileRepository  프로필 엔티티 저장소
     * @param passwordEncoder    비밀번호 암호화 도구
     */
    public UserServiceImpl(UserRepository userRepository,
                           ProfileRepository profileRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 관리자 화면에서 모든 사용자 정보를 조회하여 UserAdminDto 리스트로 반환합니다.
     *
     * <p>동작:
     * <ol>
     *   <li>userRepository.findAll()로 모든 User 엔티티 조회</li>
     *   <li>각 User에 종속된 Profile을 profileRepository.findByUser(user)로 조회</li>
     *   <li>UserAdminDto 생성자에 id, username, role, userType, email, createdAt 정보 세팅</li>
     * </ol>
     *
     * @return List<UserAdminDto> 관리자용 사용자 정보 DTO 리스트
     */
    public List<UserAdminDto> findAllForAdmin() {
        return userRepository.findAll().stream()
                .map(user -> {
                    // User에 연관된 Profile 조회 (없으면 null)
                    Profile profile = profileRepository.findByUser(user);
                    return new UserAdminDto(
                            user.getId(),
                            user.getUsername(),
                            user.getRole(),
                            user.getUserType(),
                            // profile이 존재하면 이메일, 없으면 null
                            profile != null ? profile.getEmail() : null,
                            // profile이 존재하고 establishedDate가 있으면 toString(), 없으면 null
                            profile != null && profile.getEstablishedDate() != null
                                    ? profile.getEstablishedDate().toString() : null
                    );
                }).toList();
    }

    /**
     * 사용자 회원가입을 처리합니다.
     *
     * <ol>
     *   <li>중복 username 체크 (이미 존재하면 IllegalArgumentException)</li>
     *   <li>User 엔티티 생성: username, 암호화된 password, role, userType 설정</li>
     *   <li>User 엔티티 저장</li>
     *   <li>ProfileDto → Profile 엔티티 변환(toProfileEntity)</li>
     *   <li>Profile.user에 저장된 User 엔티티 연관관계 설정</li>
     *   <li>Profile 엔티티 저장</li>
     * </ol>
     *
     * @param dto 회원가입 요청 DTO (username, password, role, userType, profile 정보 포함)
     * @throws IllegalArgumentException 이미 존재하는 사용자명일 경우
     */
    @Override
    public void register(UserRegisterDto dto) {
        // 1) username 중복 체크
        userRepository.findByUsername(dto.getUsername()).ifPresent(u -> {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        });

        // 2) User 엔티티 생성 및 비밀번호 암호화
        User user = new User(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getRole(),
                dto.getUserType()
        );
        // 3) User 저장
        userRepository.save(user);

        // 4) ProfileDto를 Profile 엔티티로 변환
        Profile profile = toProfileEntity(dto.getProfile());
        // 5) 연관관계 설정: Profile.user = 저장된 User
        profile.setUser(user);
        // 6) Profile 저장
        profileRepository.save(profile);
    }

    /**
     * 로그인 메서드 (미구현)
     *
     * @param username 로그인할 사용자명
     * @param password 로그인할 비밀번호
     * @return 인증된 User 엔티티 (예외 또는 null 반환 가능)
     */
    @Override
    public User login(String username, String password) {
        return null;  // TODO: 구현 필요
    }

    /**
     * ProfileDto를 Profile 엔티티로 변환하는 헬퍼 메서드입니다.
     *
     * @param d ProfileDto
     * @return Profile 엔티티
     */
    private Profile toProfileEntity(ProfileDto d) {
        Profile e = new Profile();
        // 공통 필드 매핑
        e.setName(d.getName());
        e.setPhone(d.getPhone());
        e.setEmail(d.getEmail());
        // 기업 전용 필드 매핑
        e.setRepresentative(d.getRepresentative());
        e.setCompanySize(d.getCompanySize());
        e.setEstablishedDate(d.getEstablishedDate());
        e.setMainIndustry(d.getMainIndustry());
        e.setAddress(d.getAddress());
        e.setEmployees(d.getEmployees());
        e.setCapital(d.getCapital());
        e.setAnnualRevenue(d.getAnnualRevenue());
        e.setHomepageUrl(d.getHomepageUrl());
        // 프리랜서 전용 필드 매핑
        e.setJobType(d.getJobType());
        e.setCareer(d.getCareer());
        e.setTechStack(d.getTechStack());
        return e;
    }
}
