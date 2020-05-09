package com.gov.customs.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "announce")
public class Announce implements Serializable {

    @Column(name = "a_u_id")
    private Long uid;

    @Id
    @Column(name = "a_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "a_title")
    private String title;

    @Column(name = "a_content")
    private String content;

    @Column(name = "a_date")
    private Timestamp date;

    public Announce(){}

    public Announce(Long uid, String title, String content, Timestamp date) {
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
