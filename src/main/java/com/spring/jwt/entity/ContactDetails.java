package com.spring.jwt.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "contact_number")
@Getter
@Setter
public class ContactDetails {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contactNumberId;

    @Column(length = 45)
    private String fullAddress;

    @Column(length = 45, nullable = false)
    private String city;

    @Column(length = 45, nullable = false)
    private Integer pinCode;

    @Column(length = 45, nullable = false, unique = true)
    private Long moNumber;

    @Column(length = 45, nullable = false)
    private Integer alternateNumber;

    @OneToOne(mappedBy = "contactNumber")
    private Status status;



}