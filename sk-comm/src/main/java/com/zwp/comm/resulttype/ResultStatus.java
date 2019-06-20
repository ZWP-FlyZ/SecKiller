package com.zwp.comm.resulttype;


public enum ResultStatus {

    SUCCESS(1000,"OK"),
    FAILD(1001,"FAILD"),



    UNAUTHORIZED(2001,"未认证"),// 未认证状态，需要进行登录
    USER__NOT_EXIT(2002,"用户不存在"),
    ERROR_LOGIN_INFO(2003,"登录信息不符合规范"),
    PASS_ERROR(2004,"用户名或者密码错误"),
    ACCESS_DENIED(2005,"无权访问");







    private int code;
    private String message;

    private ResultStatus(int code,String message){
        this.code = code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getName(){
        return this.name();
    }
}
