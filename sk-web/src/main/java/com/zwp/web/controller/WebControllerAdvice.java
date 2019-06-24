package com.zwp.web.controller;

import com.zwp.comm.resulttype.ResponseResult;
import com.zwp.comm.resulttype.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: seckiller
 * @description: 所有Controller共享的一些Bean
 * @author: zwp-flyz
 * @create: 2019-06-21 21:51
 * @version: v1.0
 **/
@ControllerAdvice
public class WebControllerAdvice {

    private final static Logger logger = LoggerFactory.getLogger(WebControllerAdvice.class);
    /**
     * 由Controller报出的未被拦截的异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public ResponseResult<String> handler(Exception ex) {
        logger.error("Unknown exception",ex);
        ResponseResult<String> res  =
                ResponseResult.build(ResultStatus.EXCEPTION);
        res.setData(ex.getMessage());
        return res;
    }


}
