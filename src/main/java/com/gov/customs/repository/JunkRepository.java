package com.gov.customs.repository;

import com.gov.customs.model.Junk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JunkRepository extends JpaRepository<Junk, Long> {
    Junk findJunkById(Long id);
    List<Junk> findJunksByCid(Long id);
    List<Junk> findJunksByNameContains(String name);
    List<Junk> findJunksByTypeContains(String type);
}
