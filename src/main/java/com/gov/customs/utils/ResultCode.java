package com.gov.customs.utils;

public enum ResultCode {
    SUCCESS(200,"成功"),

    BAD_REQUEST(400,"参数或语法错误"),
    UNAUTHORIZED(401,"认证失败"),
    FORBIDDEN(403,"禁止访问"),
    NOT_FOUND(404,"请求的资源不存在"),
    OPERATE_ERROR(405,"操作失败,请求操作的资源不存在"),
    TIME_OUT(408,"请求超时"),
    CONFLICT(409,"操作失败, 资源冲突"),

    SERVER_ERROR(500,"服务器内部错误");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
