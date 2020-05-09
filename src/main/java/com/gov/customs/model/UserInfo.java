package com.gov.customs.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "user_info")
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1366815546093762449L;
    @Id
    @Column(name = "ui_id")
    private long id = 0;

    @Column(name = "ui_identity")
    private String identity;

    @Column(name = "ui_name")
    private String name;

    @Column(name = "ui_phone")
    private String phone;

    @Column(name = "ui_mail")
    private String mail;

    @Column(name = "ui_start_date")
    private Date start_date;

    @Column(name = "ui_end_date")
    private Date end_date;

    public UserInfo(){}

    public UserInfo(Long id, String name, String identity, String phone, String mail) {
        this.id = id;
        this.identity = identity;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
    }

    public UserInfo(Long id, String name, String identity, String phone, String mail, Date start_date, Date end_date) {
        this.id = id;
        this.name = name;
        this.identity = identity;
        this.phone = phone;
        this.mail = mail;
        this.start_date = start_date;
        this.end_date = end_date;
    }
}
