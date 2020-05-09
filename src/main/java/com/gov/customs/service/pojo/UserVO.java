package com.gov.customs.service.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
public class UserVO {
    private Long id;
    private String username;
    private String realname;
    private String identity;
    private String phone;
    private String mail;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date end_date;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp register_date;
    private List<String> roles;

    public UserVO() {
    }

    public UserVO(Long id, String username, String realname, String identity, String phone, String mail, Date end_date, Timestamp register_date, List<String> roles) {
        this.id = id;
        this.username = username;
        this.realname = realname;
        this.identity = identity;
        this.phone = phone;
        this.mail = mail;
        this.end_date = end_date;
        this.register_date = register_date;
        this.roles = roles;
    }
}
