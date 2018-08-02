package com.ptteng.academy.business.consts;

/**
 * @program: canary
 * @description: 程序中公用的常量类
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 02:40
 **/

public class CommonConst {
    /**
     * 安全密码(UUID生成)，作为盐值用于用户密码的加密
     */
    public static final String SECURITY_KEY = "929123f8f17944e8b0a531045453e1f1";

    /**
     * 程序默认的错误状态码
     */
    public static final int DEFAULT_ERROR_CODE = 500;

    /**
     * 程序默认的成功状态码
     */
    public static final int DEFAULT_SUCCESS_CODE = 200;

    public static final String DEFAULT_TEMP_DIR = "/data/services/academy-canary-server/admin/temp/";
}
