package com.gov.customs.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Table(name = "topic")
@Entity
public class Topic implements Serializable {

    @Column(name = "t_u_id")
    private Long uid;

    @Id
    @Column(name = "t_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "t_title")
    private String title;

    @Column(name = "t_content")
    private String content;

    @Column(name = "t_date")
    private Timestamp date;

    public Topic(){}

    public Topic(Long uid, String title, String content, Timestamp date) {
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
