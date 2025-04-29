package com.example.yellowaution.repository;

import com.example.yellowaution.domain.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
    Optional<Freelancer> findByUserId(Long userId);
}
