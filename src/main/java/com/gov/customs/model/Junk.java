package com.gov.customs.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "junk")
public class Junk {
    @Column(name = "j_c_id")
    private Long cid;

    @Id
    @Column(name = "j_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "j_name")
    private String name;

    @Column(name = "j_type")
    private String type;

    @Column(name = "j_describe")
    private String describe;

    @Column(name = "j_date")
    private Timestamp date;

    public Junk(){}

    public Junk(Long cid, String name, String type, String describe, Timestamp date) {
        this.cid = cid;
        this.name = name;
        this.type = type;
        this.describe = describe;
        this.date = date;
    }
}
