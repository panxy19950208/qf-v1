package com.qf.v1.common.pojo;

import java.io.Serializable;

/**
 * @author HuangGuiZhao
 * @Date 2018/12/26
 * 多文件上传的情况
 */
public class MultiResultBean implements Serializable{

    //代表当前的处理结果
    //0代表成功，1代表失败
    private Integer errno;
    //当成功时，将信息注入到data中
    private String[] data;
    //当失败是，将错误提示注入到message中
    private String message;

    public static MultiResultBean errorResult(String message){
        MultiResultBean resultBean = new MultiResultBean();
        resultBean.setErrno(1);
        resultBean.setMessage(message);
        return resultBean;
    }

    public static MultiResultBean successResult(String[] data){
        MultiResultBean resultBean = new MultiResultBean();
        resultBean.setErrno(0);
        resultBean.setData(data);
        return resultBean;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
