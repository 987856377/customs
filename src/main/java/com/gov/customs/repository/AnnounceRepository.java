package com.gov.customs.repository;

import com.gov.customs.model.Announce;
import com.gov.customs.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnounceRepository extends JpaRepository<Announce, Long> {
    Announce findAnnounceById(Long id);
    List<Announce> findAnnouncesByUid(Long id);
    List<Announce> findAnnounceByTitleContains(String title);
}