package com.ptteng.academy.framework.exception;

/**
 * @program: canary
 * @description: test
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-05 15:32
 **/

public class FindNullException extends Exception {
    private static final long serialVersionUID = -2987254810585856368L;

    public FindNullException() {

    }
    public FindNullException(String message) {
        super(message);
    }
}
