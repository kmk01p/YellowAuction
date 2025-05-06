package com.example.yellowaution.repository;

import com.example.yellowaution.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * UserRepository 인터페이스는 User 엔티티에 대한
 * CRUD 및 사용자명(username) 기반 조회 기능을 제공하는
 * Spring Data JPA 리포지토리입니다.
 *
 * <p>JpaRepository를 상속함으로써 아래 기능을 자동으로 제공합니다:
 * <ul>
 *   <li>기본 CRUD 메서드 (save, findById, findAll, delete 등)</li>
 *   <li>페이징 및 정렬 기능 (Pageable, Sort 지원)</li>
 * </ul>
 * </p>
 *
 * <p>추가로, 선언한 메서드 이름만으로 쿼리를 자동 생성하는
 * Spring Data JPA의 메서드 이름 전략을 활용합니다.</p>
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 사용자명(username)을 기준으로 User 엔티티를 조회합니다.
     *
     * <p>SQL 예시:
     * <pre>
     * SELECT * FROM user
     * WHERE username = :username
     * LIMIT 1
     * </pre>
     *
     * @param username 조회할 사용자 계정명 (고유)
     * @return Optional<User>
     *         - 값이 존재하면 조회된 User 엔티티를 포함
     *         - 값이 없으면 Optional.empty()
     * @see org.springframework.data.jpa.repository.JpaRepository
     */
    Optional<User> findByUsername(String username);

}
