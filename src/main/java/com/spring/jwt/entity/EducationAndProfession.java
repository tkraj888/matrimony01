package com.spring.jwt.entity;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "educationAndProfessionalDetails")
@Getter
@Setter
public class EducationAndProfession {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer educationAndProfessionalDetailsId;

    @Column(length = 45, nullable = false)
    private String education;

    @Column(length = 45, nullable = false)
    private String degree;

    @Column(length = 45, nullable = false)
    private String occupation;

    @Column(length = 45, nullable = false)
    private String occupationDetails;

    @Column(length = 45, nullable = false)
    private Integer incomePerYear;

    @Column(length = 45)
    private String status1;

    @Column(length = 45)
    private String educationAndProfessionalDetailsCol;

    @Column
    private Integer userId;

    @OneToOne(mappedBy = "educationAndProfessionalDetails")
    private Status status;

}