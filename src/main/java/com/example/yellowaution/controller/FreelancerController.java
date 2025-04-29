package com.example.yellowaution.controller;

import com.example.yellowaution.domain.Freelancer;
import com.example.yellowaution.service.FreelancerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/freelancers")
public class FreelancerController {
    private final FreelancerService freelancerService;

    public FreelancerController(FreelancerService freelancerService) {
        this.freelancerService = freelancerService;
    }

    @GetMapping("/{userId}")
    public String detail(@PathVariable Long userId, Model model) {
        Freelancer freelancer = freelancerService.findByUserId(userId).orElseThrow();
        model.addAttribute("freelancer", freelancer);
        return "freelancer/detail";
    }
}
