package com.gov.customs.repository;

import com.gov.customs.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findTopicById(Long id);
    List<Topic> findTopicsByUid(Long id);
    List<Topic> findTopicByTitleContains(String title);
}
