package com.spring.jwt.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class PartnerPreference {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer expectationsId;

    @Column(length = 45)
    private String age;

    @Column(length = 45)
    private String lookingFor;

    @Column(length = 45)
    private String height;

    @Column(length = 45)
    private String eatingHabits;

    @Column(length = 45)
    private String countryLivingIn;

    @Column(length = 45)
    private String complexion;

    @Column(length = 45)
    private String religion;

    @Column(length = 45)
    private String caste;

    @Column(length = 45)
    private String education;

    @Column(length = 45)
    private String residentStatus;

    @Column(length = 250)
    private String preference;

    @Column(length = 45)
    private String status1;

    @Column
    private Integer userId;

    @OneToOne(mappedBy = "partnerPreference")
    private Status status;

}
