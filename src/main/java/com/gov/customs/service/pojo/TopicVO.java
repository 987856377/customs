package com.gov.customs.service.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TopicVO {
    private Long id;
    private String username;
    private String title;
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp date;

    public TopicVO(){}

    public TopicVO(Long id, String username, String title, String content, Timestamp date) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
