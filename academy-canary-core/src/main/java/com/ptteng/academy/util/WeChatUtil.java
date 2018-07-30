package com.ptteng.academy.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ptteng.academy.business.dto.WeChatTokenDto;
import com.ptteng.academy.business.dto.WeChatUserDto;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;

/**
 * @program: canary
 * @description: 微信工具类
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-19 22:29
 **/
@Slf4j
public class WeChatUtil {
    // 凭证获取（GET）
    public final static String WeChatToken_url = "https://api.weixin.qq.com/cgi-bin/WeChatTokenDto?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public final static String WeChatByCodeToken_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";
    public final static String APPID = "wx2750055a558bbe86";
    public final static String APPSECRET = "fe33aae20890da44fc14c709468b7a91";
    /**
     * 发送https请求
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }
        return jsonObject;
    }
    /**
     * 获取接口访问凭证
     *
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public static WeChatTokenDto getToken(String appid, String appsecret) {
        WeChatTokenDto WeChatTokenDto = null;
        String requestUrl = WeChatToken_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                WeChatTokenDto = new WeChatTokenDto();
                WeChatTokenDto.setAccessToken(jsonObject.getString("access_WeChatToken"));
                WeChatTokenDto.setExpiresIn(jsonObject.getIntValue("expires_in"));
            } catch (JSONException e) {
                WeChatTokenDto = null;
                // 获取WeChatToken失败
                log.error("获取WeChatToken失败 errcode:{} errmsg:{}", jsonObject.get("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return WeChatTokenDto;
    }
    /**
     * URL编码（utf-8）
     *
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }



    public static WeChatUserDto getUserInfo(String accessWeChatToken, String openId) {
        WeChatUserDto weChatUserDto = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_WeChatToken=ACCESS_WeChatToken&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_WeChatToken", accessWeChatToken).replace("OPENID", openId);
        // 获取用户信息
        JSONObject jsonObject = WeChatUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                weChatUserDto = new WeChatUserDto();
                // 用户的标识
                weChatUserDto.setOpenId(jsonObject.getString("openid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                weChatUserDto.setSubscribe(jsonObject.getIntValue("subscribe"));
                // 用户关注时间
                weChatUserDto.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // 昵称
                weChatUserDto.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weChatUserDto.setSex(jsonObject.getIntValue("sex"));
                // 用户所在国家
                weChatUserDto.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weChatUserDto.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                weChatUserDto.setCity(jsonObject.getString("city"));
                // 用户的语言，简体中文为zh_CN
                weChatUserDto.setLanguage(jsonObject.getString("language"));
                // 用户头像
                weChatUserDto.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                if (0 == weChatUserDto.getSubscribe()) {
                    log.error("用户{}已取消关注", weChatUserDto.getOpenId());
                } else {
                    int errorCode = jsonObject.getIntValue("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                }
            }
        }
        return weChatUserDto;
    }

    public static WeChatTokenDto getTokenBycode(String appid, String appsecret,String code) {
        WeChatTokenDto WeChatTokenDto = null;
        String requestUrl = WeChatByCodeToken_url.replace("APPID", appid).replace("APPSECRET", appsecret).replace("CODE", code);
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        System.out.println(jsonObject);
        if (null != jsonObject) {
            try {
                WeChatTokenDto = new WeChatTokenDto();
                WeChatTokenDto.setAccessToken(jsonObject.getString("access_WeChatToken"));
                WeChatTokenDto.setExpiresIn(jsonObject.getIntValue("expires_in"));
            } catch (JSONException e) {
                WeChatTokenDto = null;
                // 获取WeChatToken失败
                log.error("获取WeChatToken失败 errcode:{} errmsg:{}", jsonObject.get("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return WeChatTokenDto;
    }
}
