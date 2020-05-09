package com.gov.customs.service.pojo;

public enum RoleVO {
    USER((long)3, "USER"), ADMIN((long)2, "ADMIN"), DBA((long)1, "DBA");
    private Long id;
    private String name;

    private RoleVO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
