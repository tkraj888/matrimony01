package com.spring.jwt.CompleteProfile;

import com.spring.jwt.ContactDetails.ContactDetailsMapper;
import com.spring.jwt.EducationAndProfession.EducationAndProfessionMapper;
import com.spring.jwt.FamilyBackground.FamilyBackgroundMapper;
import com.spring.jwt.HoroscopeDetails.HoroscopeDetailsMapper;
import com.spring.jwt.PartnerPreference.PartnerPreferenceMapper;
import com.spring.jwt.entity.CompleteProfile;
import com.spring.jwt.exception.ResourceNotFoundException;
import com.spring.jwt.profile.UserProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompleteProfileServiceImpl implements CompleteProfileService {

    private final CompleteProfileRepository repo;


    @Override
    public CompleteProfileDTO getByUserId(Integer userId) {

        CompleteProfile cp = repo.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Complete profile not found"));

        CompleteProfileDTO dto = new CompleteProfileDTO();
        dto.setCompleteProfileId(cp.getCompleteProfileId());
        dto.setProfileCompleted(cp.isProfileCompleted());


        // nested DTO mapping
        dto.setUserProfile(cp.getUserProfile() != null ? UserProfileMapper.toDTO(cp.getUserProfile()) : null);
        dto.setHoroscopeDetails(cp.getHoroscopeDetails() != null ? HoroscopeDetailsMapper.toDTO(cp.getHoroscopeDetails()) : null);
        dto.setEducationAndProfession(cp.getEducationAndProfession() != null ? EducationAndProfessionMapper.toDTO(cp.getEducationAndProfession()) : null);
        dto.setFamilyBackground(cp.getFamilyBackground() != null ? FamilyBackgroundMapper.toDTO(cp.getFamilyBackground()) : null);
        dto.setPartnerPreference(cp.getPartnerPreference() != null ? PartnerPreferenceMapper.toDTO(cp.getPartnerPreference()) : null);
        dto.setContactDetails(cp.getContactDetails() != null ? ContactDetailsMapper.toDTO(cp.getContactDetails()) : null);

        return dto;
    }

    @Override
    public MissingProfileDTO checkMissingSections(Integer userId) {

        CompleteProfile cp = repo.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Complete profile not found"));

        MissingProfileDTO result = new MissingProfileDTO();

        result.setUserProfile(cp.getUserProfile() != null);
        result.setHoroscopeDetails(cp.getHoroscopeDetails() != null);
        result.setEducationAndProfession(cp.getEducationAndProfession() != null);
        result.setFamilyBackground(cp.getFamilyBackground() != null);
        result.setPartnerPreference(cp.getPartnerPreference() != null);
        result.setContactDetails(cp.getContactDetails() != null);
        result.setDocument(cp.getDocument() != null);

        // calculate percentage
        int total = 7;
        int completed = 0;

        if (result.isUserProfile()) completed++;
        if (result.isHoroscopeDetails()) completed++;
        if (result.isEducationAndProfession()) completed++;
        if (result.isFamilyBackground()) completed++;
        if (result.isPartnerPreference()) completed++;
        if (result.isContactDetails()) completed++;
        if (result.isDocument()) completed++;

        result.setCompletionPercentage((completed * 100) / total);

        return result;
    }

    public void recalcAndSave(CompleteProfile cp) {
        boolean completed = true;

        if (cp.getUserProfile() == null) completed = false;
        if (cp.getHoroscopeDetails() == null) completed = false;
        if (cp.getEducationAndProfession() == null) completed = false;
        if (cp.getFamilyBackground() == null) completed = false;
        if (cp.getPartnerPreference() == null) completed = false;
        if (cp.getContactDetails() == null) completed = false;

        // documents list or documentIds not empty
//        if ((cp.getDocuments() == null || cp.getDocuments().isEmpty())
//                && (cp.getDocumentIds() == null || cp.getDocumentIds().trim().isEmpty())) {
//            completed = false;
//        }

        cp.setProfileCompleted(completed);
        repo.save(cp);
    }
}
