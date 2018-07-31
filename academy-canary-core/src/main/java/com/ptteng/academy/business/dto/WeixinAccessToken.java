package com.ptteng.academy.business.dto;

/**
 * description:获取微信全局的token类（区别网页授权token）
 * author:Lin
 * Date:2018/7/29
 * Time:0:48
 */

public class WeixinAccessToken {
    private static String accessToken;

    public static void setAccessToken(String accessToken) {
        WeixinAccessToken.accessToken = accessToken;
    }

    public static String getAccessToken(){
        return accessToken;
    }
}
