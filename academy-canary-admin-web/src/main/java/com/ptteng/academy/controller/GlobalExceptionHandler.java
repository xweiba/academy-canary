package com.ptteng.academy.controller;

import com.ptteng.academy.framework.exception.MyException;
import com.ptteng.academy.framework.pojo.ErrorInfo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: canary
 * @description:
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-05 14:22
 **/

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MyException.class)
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest request, MyException e) throws Exception{
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setCode(122);
        r.setMessage("自定义异常");
        r.setData("这是数据");
        r.setUrl(request.getRequestURL().toString());
        return r;
    }
}
