package com.zwp.comm.resulttype;

/**
 * @program: seckiller
 * @description: controller层中回应前端的数据
 * @author: zwp-flyz
 * @create: 2019-06-20 15:36
 * @version: v1.0
 **/
public class ResponseResult<T> extends AbstractResult{

    private T data;

    protected ResponseResult(ResultStatus status, String message) {
        super(status, message);
    }

    protected ResponseResult(ResultStatus status) {
        super(status);
    }

    public static <T> ResponseResult<T> build(){
        ResponseResult<T> res = new ResponseResult<>(ResultStatus.SUCCESS);
        return res;
    }

    public static <T> ResponseResult<T> build(String message) {
        return new ResponseResult(ResultStatus.SUCCESS, message);
    }

    public static <T> ResponseResult<T> build(ResultStatus status) {
        return new ResponseResult(status);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
