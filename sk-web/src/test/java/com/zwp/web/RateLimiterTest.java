package com.zwp.web;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @program: seckiller
 * @description: 限流测试
 * @author: zwp-flyz
 * @create: 2019-07-16 23:25
 * @version: v1.0
 **/
public class RateLimiterTest {

    final static RateLimiter rateLimiter = RateLimiter.create(2.0);

    public static void main(String[] args) {
        long now = System.currentTimeMillis();
        while(true){
            System.err.println(rateLimiter.acquire(3));
            System.err.println(System.currentTimeMillis()-now);
        }
    }


}
