package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userProfile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userProfileId;

    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String taluka;
    private String district;
    private Integer pinCode;
    private Long mobileNumber;
    private String mail;
    private String status;
    private String gender;
    private String religion;
    private String caste;
    private String maritalStatus;
    private Integer height;
    private Integer weight;

    private String bloodGroup;
    private String complexion;
    private String diet;
    private Boolean spectacle;
    private Boolean lens;
    private Boolean physicallyChallenged;
    private String homeTownDistrict;
    private String nativeTaluka;
    private String currentCity;
    private String userProfileCol;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;



}
