package com.zwp.repo.datasourceconfig;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @program: seckiller
 * @description: 动态数据源切换数据源的切面
 * @author: zwp-flyz
 * @create: 2019-06-22 13:25
 * @version: v1.0
 **/
@Component
@Aspect
public class DynamicDatasourceAspect {
    private final static Logger LOGGER  = LoggerFactory.getLogger(DynamicDatasourceAspect.class);

    @Pointcut("@annotation(com.zwp.repo.datasourceconfig.UseDatasource)")
    public void cutpoint(){}

    @Before("cutpoint()")
    public void switchDatasource(JoinPoint point){

        Class<?> className = point.getTarget().getClass();
        String methodName = point.getSignature().getName();
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();

        DataSourceType type = DataSourceType.READ_DATASOURCE;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);

            // 判断是否存在@UseDatasource注解
            if (method.isAnnotationPresent(UseDatasource.class)) {
                UseDatasource ud = method.getAnnotation(UseDatasource.class);
                // 取出注解中的数据源名
                type = ud.value();
            }
        } catch (Exception e) {
            LOGGER.error("Error at change datasource ",e);
        }
        LOGGER.info("datasource:{} selected ",type);
        DataSourceContextHolder.setDataSource(type);
    }

    /**
     * 清除数据源选择项
     */
    @After("cutpoint()")
    public void clearDatasource(){
        DataSourceContextHolder.cleanDataSource();
    }





}
