package com.qf.miaosha.exception;

//自定义异常
//不是语法错误逻辑问题，而是业务规则上的提醒

public class SeckillException extends RuntimeException{

    public SeckillException (String message){
        super(message);
    }

}
