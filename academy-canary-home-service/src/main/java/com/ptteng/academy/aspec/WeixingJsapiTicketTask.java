package com.ptteng.academy.aspec;

import com.ptteng.academy.business.dto.WeixinAccessToken;
import com.ptteng.academy.business.dto.WexinJsapiTicket;
import com.ptteng.academy.util.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * description:
 * author:Lin
 * Date:2018/7/31
 * Time:13:53
 */
@EnableScheduling
@Component
public class WeixingJsapiTicketTask {
        private static Logger logger = LoggerFactory.getLogger(WeixinAccessTokenTask.class);


        // 第一次延迟2秒执行，当执行完后7100秒再执行
        @Scheduled(initialDelay = 2000, fixedDelay = 7000 * 1000 )
        public void getJsapiTicket() {
            try {
                WexinJsapiTicket.setJsapiTicket(WeChatUtil.getTicket(WeixinAccessToken.getAccessToken()));
                logger.info("JsapiTicket定时器启动："+WexinJsapiTicket.getJsapiTicket());
            } catch (Exception e) {
                logger.error("获取微信JsapiTicket出错，可能是ip未在白名单, 信息如下");
                e.printStackTrace();
                // 此处可能陷入死循环
            }
        }
}

