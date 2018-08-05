package com.ptteng.academy.framework.pojo;

import lombok.Data;

/**
 * @program: canary
 * @description: 错误信息
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-05 14:12
 **/

@Data
public class ErrorInfo<T> {
    public static final Integer OK = 0;
    public static final Integer ERROR = 100;

    private Integer code;
    private String message;
    private String url;
    private T data;
}
