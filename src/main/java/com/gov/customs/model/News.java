package com.gov.customs.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "news")
public class News implements Serializable {
    @Column(name = "n_u_id")
    private Long uid;

    @Id
    @Column(name = "n_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "n_title")
    private String title;

    @Column(name = "n_content")
    private String content;

    @Column(name = "n_date")
    private Timestamp date;

    public News(){}

    public News(Long uid, String title, String content, Timestamp date) {
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
