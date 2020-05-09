package com.gov.customs.service.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReplyVO {
    private Long id;
    private String username;
    private String topic;
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp date;

    public ReplyVO(){}

    public ReplyVO(Long id, String username, String topic, String content, Timestamp date) {
        this.id = id;
        this.username = username;
        this.topic = topic;
        this.content = content;
        this.date = date;
    }
}
