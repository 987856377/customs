package com.gov.customs.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "reply")
public class Reply implements Serializable {

    @Column(name = "r_u_id")
    private Long uid;

    @Column(name = "r_t_id")
    private Long tid;

    @Id
    @Column(name = "r_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "r_content")
    private String content;

    @Column(name = "r_date")
    private Timestamp date;

    public Reply(){}

    public Reply(Long uid, Long tid, String content, Timestamp date) {
        this.uid = uid;
        this.tid = tid;
        this.content = content;
        this.date = date;
    }
}
