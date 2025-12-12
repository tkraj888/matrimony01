package com.spring.jwt.CompleteProfile;
import com.spring.jwt.EducationAndProfession.EducationAndProfessionDTO;
import com.spring.jwt.profile.UserProfileDTO2;
import com.spring.jwt.HoroscopeDetails.HoroscopeDetailsDTO;
import com.spring.jwt.FamilyBackground.FamilyBackgroundDTO;
import com.spring.jwt.PartnerPreference.PartnerPreferenceDTO;
import com.spring.jwt.ContactDetails.ContactDetailsDTO;
import lombok.Data;

@Data
public class CompleteProfileDTO {

    private Integer completeProfileId;

    private UserProfileDTO2 userProfile;
    private HoroscopeDetailsDTO horoscopeDetails;
    private EducationAndProfessionDTO educationAndProfession;
    private FamilyBackgroundDTO familyBackground;
    private PartnerPreferenceDTO partnerPreference;
    private ContactDetailsDTO contactDetails;

    private boolean profileCompleted;
    private String statusCol;
    private String documentIds;
}
