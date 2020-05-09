package com.gov.customs.service.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class JunkVO {
    private Long id;
    private String name;
    private String classify;
    private String type;
    private String describe;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp date;

    public JunkVO(){}

    public JunkVO(Long id, String name, String classify, String type, String describe, Timestamp date) {
        this.id = id;
        this.name = name;
        this.classify = classify;
        this.type = type;
        this.describe = describe;
        this.date = date;
    }
}
