package com.zwp.comm.utils;

import org.springframework.util.DigestUtils;

/**
 * @program: seckiller
 * @description: MD5工具
 * @author: zwp-flyz
 * @create: 2019-06-21 11:12
 * @version: v1.0
 **/
public class MD5 {

    /**
     *  MD5编码
     * @param v
     * @return
     */
    public static String encode(String v){
        return DigestUtils.md5DigestAsHex(v.getBytes());
    }

}
