package com.ptteng.academy.util;

import com.ptteng.academy.business.consts.CommonConst;

/**
 * @program: canary
 * @description: 密码加密工具类
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-03 01:19
 **/

public class PasswordUtil {
    /**
     * AES 加密
     * @param password
     *         未加密的密码
     * @param salt
     *         盐值，默认使用用户名就可
     * @return
     * @throws Exception
     */
    public static String encrypt(String password, String salt) throws Exception {
        return AesUtil.encrypt(MD5Util.MD5(salt + CommonConst.SECURITY_KEY), password);
    }

    /**
     * AES 解密
     * @param encryptPassword
     *         加密后的密码
     * @param salt
     *         盐值，默认使用用户名就可
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptPassword, String salt) throws Exception {
        return AesUtil.decrypt(MD5Util.MD5(salt + CommonConst.SECURITY_KEY), encryptPassword);
    }
}
