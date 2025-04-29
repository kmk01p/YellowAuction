package com.example.yellowaution.service;

import com.example.yellowaution.domain.Project;
import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.ProjectRequestDto;
import com.example.yellowaution.repository.ProjectRepository;
import com.example.yellowaution.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Project createProject(ProjectRequestDto dto, Long clientId) {
        User client = userRepository.findById(clientId).orElseThrow();
        Project project = new Project();
        project.setTitle(dto.getTitle());
        project.setCategory(dto.getCategory());
        project.setDescription(dto.getDescription());
        project.setStatus("모집중");
        project.setClient(client);
        return projectRepository.save(project);
    }

    public List<Project> listProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProject(Long id) {
        return projectRepository.findById(id);
    }
}

