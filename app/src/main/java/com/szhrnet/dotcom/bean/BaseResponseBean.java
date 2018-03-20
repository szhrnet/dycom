package com.szhrnet.dotcom.bean;

/**
 * Created by ZCZ on 2017/4/21.
 */

public class BaseResponseBean<T> {
    private Integer code = 0;

    private String msg;

    private Integer status;

    private T data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
