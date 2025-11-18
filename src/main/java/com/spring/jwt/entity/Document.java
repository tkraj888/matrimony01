package com.spring.jwt.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "document")
@Getter
@Setter
public class Document {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer documentId;

    @Column(length = 45, nullable = false)
    private String documentType;

    @NotNull(message = "document data cannot be empty")
    @Lob
    @Column(name = "document_data", nullable = false)
    private byte[] documentData;

    @Column
    private Integer userId;

    @Column(length = 45)
    private String status;

    @OneToMany(mappedBy = "document")
    private Set<Status> Status = new HashSet<>();

}