package com.example.yellowaution.repository;

import com.example.yellowaution.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByCategory(String category);
    List<Project> findByClientId(Long clientId);
}
