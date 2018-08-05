package com.ptteng.academy.framework.exception;

/**
 * @program: canary
 * @description: 资源不存在
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-05 14:18
 **/

public class ResourceIsNullException extends Exception {

    private static final long serialVersionUID = 8959480665065400207L;

    public ResourceIsNullException() {

    }

    public ResourceIsNullException(String message) {
        super(message);
    }
}
