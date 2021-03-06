package com.ptteng.academy.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @program: weixin-access_token
 * @description: 获取微信所有接口权限
 * @author: Mr.xweiba
 * @create: 2018-07-04 00:58
 **/

@Slf4j
public class WechatSignUtil {
//    public static void main(String[] args) {
//        String jsapi_ticket = "jsapi_ticket";
//
//        // 注意 URL 一定要动态获取，不能 hardcode(硬编码), 签名用的url必须是调用JS接口页面的完整URL。
//        // String url = args[0];
//        String url = "academy.home.canary.dounixue.net";
//        Map<String, String> ret = sign("sM4AOVdWfPE4DxkXGEs8VJHCNcBKgdN-QGZiK33Vpz525WBgxF-HEBQoJqiDNG3RcUHZkGNhnIU7l_OpetvBNg", url);
//        for (Map.Entry entry : ret.entrySet()) {
//            System.out.println(entry.getKey() + ", " + entry.getValue());
//        }
//    }

    public static Map<String, String> getWexinAuthority(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        log.debug("待加密参数: " + string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
