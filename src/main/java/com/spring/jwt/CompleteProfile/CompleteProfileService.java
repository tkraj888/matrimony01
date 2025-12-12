package com.spring.jwt.CompleteProfile;

import com.spring.jwt.entity.CompleteProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface CompleteProfileService {



    /**
     * Recalculate profileCompleted based on presence of sections.
     * Rule (example): profileCompleted = true when these sub-entities are non-null:
     *   userProfile, horoscopeDetails, educationAndProfession, familyBackground,
     *   partnerPreference, contactDetails, documents (or documentIds)
     *
     * You can adjust business rule as required.
     */
    void recalcAndSave(CompleteProfile cp);

    CompleteProfileDTO getByUserId(Integer userId);
    MissingProfileDTO checkMissingSections(Integer userId);


}
