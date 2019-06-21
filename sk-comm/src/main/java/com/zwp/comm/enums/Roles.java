package com.zwp.comm.enums;


/**
 * @program: seckiller
 * @description: 用户角色
 * @author: zwp-flyz
 * @create: 2019-06-20 15:16
 * @version: v1.0
 *
 **/

public enum Roles {

    ROLE_USER(0,"用户"),
    ROLE_SELLER(1,"商家"),
    ROLE_ADMIN(2,"管理员");

    private int code;
    private String message;
    private Roles(int code,String message){
        this.code = code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public String getName(){
        return this.name();
    }

}
