package com.spring.jwt.CompleteProfile;

import lombok.Data;

@Data
public class MissingProfileDTO {

    private boolean userProfile;
    private boolean horoscopeDetails;
    private boolean educationAndProfession;
    private boolean familyBackground;
    private boolean partnerPreference;
    private boolean contactDetails;
    private boolean document;

    private Integer completionPercentage;
}
