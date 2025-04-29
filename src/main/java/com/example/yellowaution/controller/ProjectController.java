package com.example.yellowaution.controller;

import com.example.yellowaution.domain.Project;
import com.example.yellowaution.dto.ProjectRequestDto;
import com.example.yellowaution.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("projects", projectService.listProjects());
        return "project/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Project project = projectService.getProject(id).orElseThrow();
        model.addAttribute("project", project);
        return "project/detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("projectRequestDto", new ProjectRequestDto());
        return "project/form";
    }

    @PostMapping
    public String create(@ModelAttribute ProjectRequestDto dto, @RequestParam Long clientId) {
        projectService.createProject(dto, clientId);
        return "redirect:/projects";
    }
}
