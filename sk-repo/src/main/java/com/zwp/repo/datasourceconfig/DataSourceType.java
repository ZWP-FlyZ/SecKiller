package com.zwp.repo.datasourceconfig;

/**
 * @program: seckiller
 * @description: 数据源类型
 * @author: zwp-flyz
 * @create: 2019-06-22 12:33
 * @version: v1.0
 **/
public enum DataSourceType {
    READ_DATASOURCE(0,"读-数据源"),
    WRITE_DATASOURCE(1,"写-数据源");

    private int code;
    private String message;

    DataSourceType(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getName(){
        return this.name();
    }
}
