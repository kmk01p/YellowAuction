package com.example.yellowaution.service;

import com.example.yellowaution.domain.Freelancer;
import com.example.yellowaution.domain.User;
import com.example.yellowaution.repository.FreelancerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FreelancerService {
    private final FreelancerRepository freelancerRepository;

    public FreelancerService(FreelancerRepository freelancerRepository) {
        this.freelancerRepository = freelancerRepository;
    }

    public Freelancer registerFreelancer(String name, String skill, int experience, User user) {
        Freelancer f = new Freelancer();
        f.setName(name);
        f.setSkill(skill);
        f.setExperience(experience);
        f.setUser(user);
        return freelancerRepository.save(f);
    }

    public Optional<Freelancer> findByUserId(Long userId) {
        return freelancerRepository.findByUserId(userId);
    }
}
