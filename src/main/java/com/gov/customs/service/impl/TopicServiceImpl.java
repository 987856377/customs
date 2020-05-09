package com.gov.customs.service.impl;

import com.gov.customs.model.Topic;
import com.gov.customs.model.User;
import com.gov.customs.repository.TopicRepository;
import com.gov.customs.repository.UserRepository;
import com.gov.customs.service.TopicService;
import com.gov.customs.service.pojo.TopicVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("topicService")
@Transactional
public class TopicServiceImpl implements TopicService {
    @Resource
    UserRepository userRepository;

    @Resource
    TopicRepository topicRepository;

    @Override
    public boolean createOrUpdate(Topic topic) {
        if (topic!=null){
            topicRepository.saveAndFlush(topic);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOne(Long id) {
        if (id!=null){
            topicRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<TopicVO> findTopicsByUid(Long id) {
        if (id!=null){
            User user;
            List<TopicVO> topicVOList = new ArrayList<>();
            List<Topic> topicList = topicRepository.findTopicsByUid(id);
            if (topicList==null){
                return null;
            }
            for (Topic topic : topicList){
                user = userRepository.findUserById(topic.getUid());
                if (user==null){
                    return null;
                }
                topicVOList.add(new TopicVO(topic.getId(), user.getUsername(), topic.getTitle(), topic.getContent(), topic.getDate()));
            }
            return topicVOList;
        }
        return null;
    }

    @Override
    public List<TopicVO> findTopicByTitleContains(String title) {
        if (title!=null){
            User user;
            List<TopicVO> topicVOList = new ArrayList<>();
            List<Topic> topicList = topicRepository.findTopicByTitleContains(title);
            if (topicList==null){
                return null;
            }
            for (Topic topic : topicList){
                user = userRepository.findUserById(topic.getUid());
                if (user==null){
                    return null;
                }
                topicVOList.add(new TopicVO(topic.getId(), user.getUsername(), topic.getTitle(), topic.getContent(), topic.getDate()));
            }
            return topicVOList;
        }
        return null;
    }

    @Override
    public List<TopicVO> findAll() {
        User user;
        List<TopicVO> topicVOList = new ArrayList<>();
        List<Topic> topicList = topicRepository.findAll();
        if (topicList==null){
            return null;
        }
        for (Topic topic : topicList){
            user = userRepository.findUserById(topic.getUid());
            if (user==null){
                return null;
            }
            topicVOList.add(new TopicVO(topic.getId(), user.getUsername(), topic.getTitle(), topic.getContent(), topic.getDate()));
        }
        return topicVOList;
    }
}
