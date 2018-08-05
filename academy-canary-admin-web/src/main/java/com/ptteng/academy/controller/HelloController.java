package com.ptteng.academy.controller;

import com.ptteng.academy.framework.exception.MyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: canary
 * @description:
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-05 14:19
 **/

@RestController
public class HelloController {
    @GetMapping("/test")
    public String json() throws MyException {
        throw new MyException("发生了自定义异常");
    }
}
