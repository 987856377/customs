package com.gov.customs.service;

import com.gov.customs.model.Reply;
import com.gov.customs.service.pojo.ReplyVO;

import java.util.List;

public interface ReplyService {
    boolean createOrUpdate(Reply reply);
    boolean deleteOne(Long id);
    List<ReplyVO> findReplysByTid(Long id);
    List<ReplyVO> findReplysByUid(Long id);
}
