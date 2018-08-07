package com.ptteng.academy.business.dto;

/**
 * description:
 * author:Lin
 * Date:2018/7/31
 * Time:14:00
 */
public class WexinJsapiTicket {
    private static String jsapiTicket;

    public static void setJsapiTicket(String accessToken) {
        // 这里由计划任务在项目启动时获取jsapiTicket, 需要将ip加到微信白名单才能获取到.
        WexinJsapiTicket.jsapiTicket = accessToken;
    }

    public static String getJsapiTicket(){
        return jsapiTicket;
    }
}
