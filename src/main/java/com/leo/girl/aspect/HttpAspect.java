/**
 * @author:Leo
 * @create 2018/4/19
 * @desc
 */
package com.leo.girl.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.leo.girl.controller.GirlController.*(..))")
    public void log(){
    }

    @Before("log()")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //url
        LOGGER.info("url = {}", request.getRequestURL());
        LOGGER.info("uri = {}", request.getRequestURI());
        // method
        LOGGER.info("method = {}", request.getMethod());
        // ip
        LOGGER.info("ip = {}", request.getRemoteAddr());
        // 类方法
        LOGGER.info("class_method = {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        // 参数
        LOGGER.info("args = {}", joinPoint.getArgs());
    }

    @After("log()")
    public void after() {
        LOGGER.info("after");
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        //System.out.println("after return: " + object.toString());
    }
}
