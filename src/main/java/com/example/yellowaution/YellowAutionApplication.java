package com.example.yellowaution;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class YellowAutionApplication {
    public static void main(String[] args) {
        SpringApplication.run(YellowAutionApplication.class, args);
    }

    @Bean
    CommandLineRunner initAdmin(UserRepository repo) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("1234");
                admin.setRole("ADMIN");
                admin.setUserType("ADMIN");
                repo.save(admin);
            }
        };
    }
}

