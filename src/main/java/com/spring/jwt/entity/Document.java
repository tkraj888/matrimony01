package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "document")
@Getter
@Setter
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer documentId;

    // Example: "Aadhaar", "PAN", "Resume"
    @Column(nullable = false, length = 50)
    private String documentName;

    @Column(nullable = false, length = 150)
    private String fileName;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB", nullable = false)
    private byte[] fileData;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //    @Column(length = 45)
//    private String status1;
//
    @ManyToOne
    @JoinColumn(name = "complete_profile_id")
    private CompleteProfile completeProfile;

}