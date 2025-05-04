package com.example.yellowaution.repository;

import com.example.yellowaution.domain.Profile;
import com.example.yellowaution.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findByUserId(Long userId);

    Profile findByUser(User user);
}
