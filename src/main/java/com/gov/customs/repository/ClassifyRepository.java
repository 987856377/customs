package com.gov.customs.repository;

import com.gov.customs.model.Classify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassifyRepository extends JpaRepository<Classify, Long> {
    Classify findClassifyById(Long id);
    Classify findClassifyByName(String name);
}
