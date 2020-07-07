package com.caishi.constant;

/**
 *状态消息的封装
 */

public enum StatusEnum {

    REQUEST_NULL(200,"result is null"),
    SUCCEED(200,"succeed"),
    ERROR(404,"request error"),
    AUTH_ERROR(50001,"用户名或者密码错误"),
    AUTH_CODE_ERROR(50048,"验证码错误");

    //状态码
    private int statu;
    //状态消息
    private String msg;

    StatusEnum(int statu, String msg) {
        this.statu = statu;
        this.msg = msg;
    }

    public int getStatu() {
        return statu;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
