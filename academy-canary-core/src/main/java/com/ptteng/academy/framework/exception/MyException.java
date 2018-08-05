package com.ptteng.academy.framework.exception;

/**
 * @program: canary
 * @description: 自定义异常
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-05 14:18
 **/

public class MyException extends Exception {

    public MyException(String message) {
        super(message);
    }
}
