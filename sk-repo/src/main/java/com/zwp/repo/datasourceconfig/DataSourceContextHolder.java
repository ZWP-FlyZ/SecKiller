package com.zwp.repo.datasourceconfig;

/**
 * @program: seckiller
 * @description: 数据源Holder
 * @author: zwp-flyz
 * @create: 2019-06-22 12:31
 * @version: v1.0
 **/
public class DataSourceContextHolder {

    private static ThreadLocal<Object> threadLocal = new ThreadLocal(){
        @Override
        protected Object initialValue() {
            //默认获取读数据库
            return DataSourceType.READ_DATASOURCE;
        }
    };

    /**
     * 设置当前线程的数据源
     * @param dt
     */
    public static void setDataSource(DataSourceType dt){
        threadLocal.set(dt);
    }

    public static Object getDataSource(){
        return threadLocal.get();
    }

    public static void cleanDataSource(){
        threadLocal.remove();
    }



}
