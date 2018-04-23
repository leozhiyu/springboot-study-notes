/**
 * @author:Leo
 * @create 2018/4/23
 * @desc   自定义异常
 */
package com.leo.girl.exception;

import com.leo.girl.enums.ResultEnum;

public class GirlException extends RuntimeException{
    private Integer code;

    public GirlException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
