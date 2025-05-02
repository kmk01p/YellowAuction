package com.example.yellowaution.controller.page;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/free")
public class FreelancerController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "free/dashboard";
    }
}