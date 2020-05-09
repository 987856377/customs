package com.gov.customs.repository;

import com.gov.customs.model.Reply;
import com.gov.customs.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findReplysByTid(Long id);
    List<Reply> findReplysByUid(Long id);
}
