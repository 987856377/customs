package com.gov.customs.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "classify")
public class Classify {

    @Id
    @Column(name = "c_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_name")
    private String name;

    public Classify(){}

    public Classify(String name) {
        this.name = name;
    }
}
