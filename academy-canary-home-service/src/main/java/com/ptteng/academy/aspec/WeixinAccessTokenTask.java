package com.ptteng.academy.aspec;

import com.ptteng.academy.business.dto.WeixinAccessToken;
import com.ptteng.academy.util.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * description:
 * author:Lin
 * Date:2018/7/29
 * Time:0:34
 */

@EnableScheduling
@Component
public class WeixinAccessTokenTask {
    private static Logger logger = LoggerFactory.getLogger(WeixinAccessTokenTask.class);


    // 第一次延迟1秒执行，当执行完后7100秒再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 7000 * 1000 )
    public void getWeixinAccessToken(){
        try {
            String token = WeChatUtil.getToken(WeChatUtil.APPID, WeChatUtil.APPSECRET).getAccess_token();
            WeixinAccessToken.setAccessToken(token);
            logger.info("获取到的微信accessToken为"+token);
        } catch (Exception e) {
            logger.error("获取微信adcessToken出错，信息如下");
            e.printStackTrace();
            // 此处可能陷入死循环
        }

    }

}

