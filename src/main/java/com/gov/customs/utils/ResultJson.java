package com.gov.customs.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultJson<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public static ResultJson success(){
        return success("");
    }

    public static ResultJson success(Object o){
        return new ResultJson(ResultCode.SUCCESS, o);
    }

    public static ResultJson failure(ResultCode resultCode){
        return failure(resultCode, "");
    }

    public static ResultJson failure(ResultCode resultCode, Object o){
        return new ResultJson(resultCode,o);
    }


    public ResultJson(ResultCode resultCode){
        setResultCode(resultCode);
    }

    public ResultJson(ResultCode resultCode, T data){
        setResultCode(resultCode);
        this.data = data;
    }

    public void setResultCode(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }
}
