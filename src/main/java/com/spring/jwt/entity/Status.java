package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Status {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusId;

    @Column(length = 45)
    private String statusCol;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_user_profile_id", nullable = false)
    private UserProfile userProfile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "horoscope_details_horoscope_details_id", nullable = false)
    private HoroscopeDetails horoscopeDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_and_professional_details_id", nullable = false)
    private EducationAndProfession educationAndProfessionalDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familyBackground_id", nullable = false)
    private FamilyBackground familyBackground;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_preference_expectations_id", nullable = false)
    private PartnerPreference partnerPreference;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_number_contact_number_id", nullable = false)
    private ContactDetails contactNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_document_id", nullable = false)
    private Document document;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user_id", nullable = false)
    private User user;

}
