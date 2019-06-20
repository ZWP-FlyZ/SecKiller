package com.zwp.comm.resulttype;

/**
 * @program: seckiller
 * @description: 抽象结果类型
 * @author: zwp-flyz
 * @create: 2019-06-20 15:16
 * @version: v1.0
 **/
public abstract class AbstractResult {

    ResultStatus status;
    int code;
    String message;

    protected AbstractResult(ResultStatus status,String message){
        this.status = status;
        this.code = status.getCode();
        this.message=message;
    }

    protected AbstractResult(ResultStatus status){
        this.status = status;
        this.code = status.getCode();
        this.message=status.getMessage();
    }

    public ResultStatus getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
