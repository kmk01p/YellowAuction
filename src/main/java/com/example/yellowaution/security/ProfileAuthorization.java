package com.example.yellowaution.security;

import com.example.yellowaution.domain.Profile;
import com.example.yellowaution.repository.ProfileRepository;
import org.springframework.stereotype.Component;

@Component
public class ProfileAuthorization {

    private final ProfileRepository profileRepository;

    public ProfileAuthorization(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public boolean checkOwner(Long profileId, Long userId) {
        return profileRepository.findById(profileId)
                .map(profile -> profile.getUser().getId().equals(userId))
                .orElse(false);
    }
}
