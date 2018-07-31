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
        WexinJsapiTicket.jsapiTicket = accessToken;
    }

    public static String getJsapiTicket(){
        return jsapiTicket;
    }
}
