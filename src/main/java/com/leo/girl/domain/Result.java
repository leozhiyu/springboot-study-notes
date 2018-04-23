/**
 * @author:Leo
 * @create 2018/4/23
 * @desc HTTP请求返回的最外层对象
 */
package com.leo.girl.domain;

public class Result<T> {
    /** 具体的内容 */
    private Integer code;

    /** 提示信息 */
    private String msg;

    /** 返回的数据 */
    private T data;

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
