package com.gov.customs.service;

import com.gov.customs.model.Topic;
import com.gov.customs.service.pojo.TopicVO;

import java.util.List;

public interface TopicService {
    boolean createOrUpdate(Topic topic);
    boolean deleteOne(Long id);
    List<TopicVO> findTopicsByUid(Long id);
    List<TopicVO> findTopicByTitleContains(String title);
    List<TopicVO> findAll();
}
