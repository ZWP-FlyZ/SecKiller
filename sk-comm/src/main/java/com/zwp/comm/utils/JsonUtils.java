package com.zwp.comm.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @program: seckiller
 * @description: 全局Json转换工具
 * @author: zwp-flyz
 * @create: 2019-06-20 18:41
 * @version: v1.0
 **/
public class JsonUtils {

    // 线程安全
    private static final Gson g =new Gson();


    public static String toJson(Object o){
            return g.toJson(o);
    }

    public static <T> T fromJson(String json,Class<T> clazz){
        return g.fromJson(json,clazz);
    }

}
