package com.zwp.repo.redis;

import lombok.Data;

/**
 * @program: seckiller
 * @description: Redis配置属性
 * @author: zwp-flyz
 * @create: 2019-06-26 15:50
 * @version: v1.0
 **/
@Data
public class RedisConfigProperty {
    private String host;
    private String password;
    private Integer port;
    private boolean pooling;
}
