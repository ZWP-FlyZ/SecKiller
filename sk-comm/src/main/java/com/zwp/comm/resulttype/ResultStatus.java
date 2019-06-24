package com.zwp.comm.resulttype;


public enum ResultStatus {

    SUCCESS(1000,"OK"),
    FAILD(1001,"FAILD"),
    EXCEPTION(1002,"系统异常"),


    UNAUTHORIZED(2001,"未认证"),// 未认证状态，需要进行登录
    USER__NOT_EXIT(2002,"用户不存在"),
    ERROR_LOGIN_INFO(2003,"登录信息不符合规范"),
    PASS_ERROR(2004,"用户名或者密码错误"),
    ACCESS_DENIED(2005,"无权访问"),

    UNVERIFIED(2500,"无验证码信息"),//必须先请求验证码
    ERROR_REGISTER_INFO(2501,"不合法注册信息"),
    ERROR_VERIFY_CODE(2502,"验证码错误"),
    EXIT_USERNAME(2503,"用户名已存在"),

    INCURRENT_SKGOODS_INFO(3000,"错误货物数据");




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
