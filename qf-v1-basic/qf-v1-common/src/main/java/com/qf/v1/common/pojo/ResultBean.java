package com.qf.v1.common.pojo;

import java.io.Serializable;

public class ResultBean<T> implements Serializable{

    //代表当前处理结果
    //0代表成功，1代表失败
    private Integer errorNo;

    //当成功时将信息注入到data
    private T data;

    //当时失败时，将错误提示注入到message
    private String message;

    public ResultBean() {
    }

    public ResultBean(Integer errorNo, T data) {
        this.errorNo = errorNo;
        this.data = data;
    }

    public ResultBean(Integer errorNo, T data, String message) {
        this.errorNo = errorNo;
        this.data = data;
        this.message = message;
    }

    public static ResultBean errorResult(String message){
        ResultBean resultBean = new ResultBean();
        resultBean.setErrorNo(1);
        resultBean.setMessage(message);
        return resultBean;
    }

    public static ResultBean successResult(String data){
        ResultBean resultBean = new ResultBean();
        resultBean.setErrorNo(0);
        resultBean.setData(data);
        return resultBean;
    }

    public Integer getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(Integer errorNo) {
        this.errorNo = errorNo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
