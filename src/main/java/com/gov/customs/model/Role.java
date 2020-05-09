package com.gov.customs.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 3743774627141615707L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_id")
    private Long id;

    @Column(name = "r_name")
    private String name;

    @Column(name = "r_flag")
    private String flag;

    public Role() {
    }

    public Role(Long id, String name, String flag) {
        this.id = id;
        this.name = name;
        this.flag = flag;
    }

    public static List<String> getRoleFlags(List<Role> roles){
        List<String> flags = new ArrayList<>();
        for (Role r : roles){
            flags.add(r.getFlag());
        }
        return flags;
    }
}
