/**
 * @author:Leo
 * @create 2018/4/23
 * @desc 异常处理类
 */
package com.leo.girl.handle;

import com.leo.girl.domain.Result;
import com.leo.girl.enums.ResultEnum;
import com.leo.girl.exception.GirlException;
import com.leo.girl.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof GirlException) {
            GirlException girlException = (GirlException) e;
            return ResultUtil.error(girlException.getCode(), girlException.getMessage());
        } else {
            logger.error("【系统异常】 {}" , ResultEnum.UNKNOW_ERROR.getMsg());
            return ResultUtil.error(-1, ResultEnum.UNKNOW_ERROR.getMsg());
        }
    }
}
