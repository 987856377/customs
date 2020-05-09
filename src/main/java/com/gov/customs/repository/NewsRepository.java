package com.gov.customs.repository;

import com.gov.customs.model.Announce;
import com.gov.customs.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    News findNewsById(Long id);
    List<News> findNewsByUid(Long id);
    List<News> findNewsByTitleContains(String title);
}
