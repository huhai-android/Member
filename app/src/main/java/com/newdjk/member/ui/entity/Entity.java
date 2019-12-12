package com.newdjk.member.ui.entity;

/**
 * Created by user on 2018/4/17.
 */

public class Entity {
    private int Code;
    private Object Data;
    private String Message;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        this.Code = code;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        this.Data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String msg) {
        this.Message = msg;
    }
}
