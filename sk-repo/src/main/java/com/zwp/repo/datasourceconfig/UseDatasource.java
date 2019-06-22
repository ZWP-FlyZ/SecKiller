package com.zwp.repo.datasourceconfig;

import java.lang.annotation.*;

/**
 * @program: seckiller
 * @description: 在服务中选择数据源的注解
 * @author: zwp-flyz
 * @create: 2019-06-22 13:18
 * @version: v1.0
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface UseDatasource {

    // 默认读数据库
    DataSourceType value() default DataSourceType.READ_DATASOURCE;
}
