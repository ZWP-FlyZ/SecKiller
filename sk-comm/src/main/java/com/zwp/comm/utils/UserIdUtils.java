package com.zwp.comm.utils;

/**
 * @program: seckiller
 * @description: userId创建工具
 * @author: zwp-flyz
 * @create: 2019-06-21 17:04
 * @version: v1.0
 **/
public class UserIdUtils {

    private final static long M = 100000000000000L; // 10^14

    /**
     * 由用户名创建一个数字ID，简单版
     * @param username
     * @return
     */
    public static long getUserId(String username){
        return (Long.MAX_VALUE/2 + username.hashCode())%M;
    }
}
