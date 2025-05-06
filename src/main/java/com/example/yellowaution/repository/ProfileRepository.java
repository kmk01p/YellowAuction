package com.example.yellowaution.repository;

import com.example.yellowaution.domain.Profile;
import com.example.yellowaution.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * ProfileRepository 인터페이스는 Profile 엔티티에 대한 CRUD 및
 * 사용자별 프로필 조회 기능을 제공하는 Spring Data JPA 리포지토리입니다.
 *
 * <p>기본적으로 JpaRepository를 상속받아:
 * <ul>
 *   <li>save, findById, findAll, delete 등 기본 CRUD 메서드 사용 가능</li>
 *   <li>커스텀 쿼리 메서드 선언만으로 자동 구현</li>
 * </ul>
 * </p>
 *
 * <p>주요 제공 메서드:
 * <ul>
 *   <li>findByUserId: 특정 사용자 ID로 모든 프로필 조회</li>
 *   <li>findByEmail: 이메일로 프로필 단건 조회</li>
 *   <li>findByUser: User 엔티티로 프로필 단건 조회</li>
 * </ul>
 * </p>
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    /**
     * 이메일 주소로 프로필을 단건 조회합니다.
     *
     * <p>사용 예:
     * <pre>
     * Optional<Profile> opt = profileRepo.findByEmail("user@example.com");
     * if (opt.isPresent()) {
     *     Profile profile = opt.get();
     * }
     * </pre>
     *
     * @param email 프로필에 등록된 이메일 주소
     * @return Optional<Profile> — 이메일이 일치하는 프로필이 있으면 해당 Profile, 없으면 Optional.empty()
     */
    Optional<Profile> findByEmail(String email);

    /**
     * User 엔티티로 프로필을 단건 조회합니다.
     *
     * <p>1:1 관계로 설계된 경우, 해당 사용자가 가진 프로필을 직접 조회할 때 유용합니다.</p>
     *
     * <p>사용 예:
     * <pre>
     * Profile profile = profileRepo.findByUser(currentUser);
     * </pre>
     *
     * @param user 프로필을 소유한 User 엔티티
     * @return Profile — 해당 사용자의 프로필 (없을 경우 예외 발생 가능하므로, 존재 여부 확인 필요)
     */
    Profile findByUser(User user);

    Collection<Object> findByUserId(Long id);
}
