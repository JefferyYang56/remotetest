package com.jeffery.spzx.common.exception;

import com.jeffery.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class JefferyException extends RuntimeException{
    private Integer code;
    private String message;
    private ResultCodeEnum resultCode;
    public JefferyException(ResultCodeEnum resultCode){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.resultCode = resultCode;
    }
}
