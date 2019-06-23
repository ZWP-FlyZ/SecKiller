package com.zwp.comm.utils;

/**
 * @program: seckiller
 * @description: 密码加密工具
 * @author: zwp-flyz
 * @create: 2019-06-21 18:02
 * @version: v1.0
 **/
public class PassEncUtils {

    public final static String SALT_SPILTER="<-SALT->";

    /**
     * 返回长度不超过10的salt字符串
     * @return
     */
    public static String salt(){
        String s = "zwp-"+System.currentTimeMillis()+"-N_yZ-"+Math.random();
        return MD5.encode(s).substring(0,9);
    }

    /**
     * 密码加密
     * @param password
     * @param salt
     * @return
     */
    public static String encodePassword(String password,String salt){
        StringBuilder sb = new StringBuilder();
        sb.append(salt.charAt(0))
                .append(password.charAt(password.length()-1))
                .append(password)
                .append(password.charAt(0))
                .append(salt.charAt(salt.length()-1))
                .append(salt.substring(salt.length()/2));
        String t = sb.toString();
        t = MD5.encode(t);
        t = MD5.encode(t+sb.toString());
        return t;
    }


}
