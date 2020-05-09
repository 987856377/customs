package com.gov.customs.service.impl;

import com.gov.customs.model.Reply;
import com.gov.customs.model.Topic;
import com.gov.customs.model.User;
import com.gov.customs.repository.ReplyRepository;
import com.gov.customs.repository.TopicRepository;
import com.gov.customs.repository.UserRepository;
import com.gov.customs.service.ReplyService;
import com.gov.customs.service.pojo.ReplyVO;
import com.gov.customs.service.pojo.TopicVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("replyService")
@Transactional
public class ReplyServiceImpl implements ReplyService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private TopicRepository topicRepository;

    @Resource
    private ReplyRepository replyRepository;

    @Override
    public boolean createOrUpdate(Reply reply) {
        if (reply!=null){
            replyRepository.saveAndFlush(reply);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOne(Long id) {
        if (id!=null){
            replyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ReplyVO> findReplysByUid(Long id) {
        if (id!=null){
            User user;
            Topic topic;
            List<ReplyVO> replyVOList = new ArrayList<>();
            List<Reply> replyList = replyRepository.findReplysByUid(id);
            if (replyList==null){
                return null;
            }
            for (Reply reply : replyList){
                user = userRepository.findUserById(reply.getUid());
                if (user==null){
                    return null;
                }
                topic = topicRepository.findTopicById(reply.getTid());
                replyVOList.add(new ReplyVO(reply.getId(), user.getUsername(), topic.getTitle(), reply.getContent(), reply.getDate()));
            }
            return replyVOList;
        }
        return null;
    }

    @Override
    public List<ReplyVO> findReplysByTid(Long id) {
        if (id!=null){
            User user;
            Topic topic;
            List<ReplyVO> replyVOList = new ArrayList<>();
            List<Reply> replyList = replyRepository.findReplysByTid(id);
            if (replyList==null){
                return null;
            }
            for (Reply reply : replyList){
                user = userRepository.findUserById(reply.getUid());
                if (user==null){
                    return null;
                }
                topic = topicRepository.findTopicById(reply.getTid());
                if (topic==null){
                    return null;
                }
                replyVOList.add(new ReplyVO(reply.getId(), user.getUsername(), topic.getTitle(), reply.getContent(), reply.getDate()));
            }
            return replyVOList;
        }
        return null;
    }
}
