package com.gov.customs.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * JPA提供的四种标准用法为TABLE,SEQUENCE,IDENTITY,AUTO.
 * TABLE：使用一个特定的数据库表格来保存主键。
 * SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。
 * IDENTITY：主键由数据库自动生成（主要是自动增长型）
 * AUTO：主键由程序控制。
 *
 *
 * FetchType.LAZY: 懒加载，在访问关联对象的时候加载(即从数据库读入内存)
 * FetchType.EAGER:立刻加载，在查询主对象的时候同时加载关联对象。
 *
 * 1.一对一和多对一的@JoinColumn注解的都是在“主控方”，都是本表指向外表的外键名称。
 * 2.一对多的@JoinColumn注解在“被控方”，即一的一方，指的是外表中指向本表的外键名称。
 * 3.多对多中，joinColumns写的都是本表在中间表的外键名称，
 *   inverseJoinColumns写的是另一个表在中间表的外键名称。
 */

@Entity
@Table(name = "user")
@Data
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 7237729978037472653L;
    @Id
    @Column(name = "u_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // 设置主键自动增长
    private Long id;

    @Column(name = "u_username")
    private String username;

    @Column(name = "u_password")
    private String password;

    @Column(name = "u_register_date")
    private Timestamp register_date;

    public User(){}

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {
                    @JoinColumn(name = "ur_u_id", referencedColumnName = "u_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ur_r_id", referencedColumnName = "r_id")
            }
    )

    private List<Role> roles;

    public User(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<>();
        List<Role> roles = getRoles();
        for (Role role : roles){
            auth.add(new SimpleGrantedAuthority(role.getFlag()));
        }
        return auth;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
