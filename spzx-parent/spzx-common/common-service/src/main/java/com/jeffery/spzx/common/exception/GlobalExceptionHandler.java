package com.jeffery.spzx.common.exception;

import com.jeffery.spzx.model.vo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(JefferyException.class)
    @ResponseBody
    public Result error(JefferyException e){
        return Result.build(null, e.getResultCode());
    }
}
