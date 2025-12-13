package com.spring.jwt.Document;

import com.spring.jwt.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Integer> {

    Optional<Object> findByUserIdAndDocumentName(Integer userId, String documentName);
}
