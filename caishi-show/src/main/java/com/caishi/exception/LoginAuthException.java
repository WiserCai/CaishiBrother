package com.caishi.exception;

import com.caishi.constant.StatusEnum;

/**
 * @Author CYC
 * @Version 1.0.0
 * @Description
 **/
public class LoginAuthException extends RuntimeException{

    private int  status;
    private String message;

    public LoginAuthException() {
        super();
    }

    public LoginAuthException(String message, int status) {
        super(message);
        this.status = status;
    }


    public LoginAuthException(StatusEnum status) {
        super(status.getMsg());
        this.status = status.getStatu();
    }
}
