package com.ptteng.academy.util;

/**
 * description:
 * author:Lin
 * Date:2018/8/4
 * Time:10:58
 */


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

    /**
     * @program: SSM_WEB
     * @description: SendCloudMail SDK 工具类
     * @author: Mr.xweiba
     * @create: 2018-06-02 23:39
     **/

    @Component
    public class MailUtil {

        private static Logger logger = LoggerFactory.getLogger(MailUtil.class);

        // 认证
        @Value("mail.apiUser")
        private String apiUser;
        @Value("mail.apiKey")
        private String apiKey;
        // 邮件发送接口
        @Value("mail.apiUrl")
        private String apiUrl;

        @Autowired
        private StringRedisTemplate stringRedisTemplate;

        /* 设置默认值 */
        // 邮件主体
        private String subject = "求学大作战验证";
        // 发件人名称
        private String fromName = "李荣浩";

        // 构建http请求
        private HttpPost httpPost;
        private CloseableHttpClient httpClient;

        // http 发送内容
        List<NameValuePair> params;

        // 外部调用发送方法
        // 只提供发送邮箱
        public boolean sendMail(String email) {
            return sendMailReal(email,subject,fromName);
        }

        // 发送方法
        private boolean sendMailReal(String email, String subject, String fromName) {
            // 拼接验证url httpUrl 当前项目的根目录
            String randInt = RandomUtil .getRandomCode(6);
            stringRedisTemplate.opsForValue().set(randInt, email);
            String sendBody = "<style type=\"text/css\">html,\n" +
                    "    body {\n" +
                    "        margin: 0;\n" +
                    "        padding: 0;\n" +
                    "    }\n" +
                    "</style>\n" +
                    "<center>\n" +
                    "<table style=\"background:#f6f7f2;font-size:13px;font-family:microsoft yahei;\">\n" +
                    "\t<tbody>\n" +
                    "\t\t<tr>\n" +
                    "\t\t\t<td>\n" +
                    "\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600px\">\n" +
                    "\t\t\t\t<tbody>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
                    "\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:120px;background: url(http://7xi9bi.com1.z0.glb.clouddn.com/35069/2015/07/20/5891eacba5ba41f389168121f08be02f.jpg) no-repeat;\" width=\"538px\">\n" +
                    "\t\t\t\t\t\t\t<tbody>\n" +
                    "\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t<td height=\"70px\" valign=\"middle\" width=\"100%\"><img alt=\"logo\" src=\"http://7xi9bi.com1.z0.glb.clouddn.com/35069/2015/07/20/1686ccdd7919429a8beeb4f3f15d5eb1.png\" style=\"margin-left:50px;\" /></td>\n" +
                    "\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
                    "\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background:#fff;height:411px;\" width=\"465px\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"color:#666;line-height:1.5\" valign=\"top\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"width:360px;text-align:left;margin-top:50px;margin-bottom:80px;\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<p>亲爱的用户您好：</p>\n" +
                    "\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"text-indent:2em\">欢迎您使用求学大作站平台，我们将为您提供优质的服务。您的验证码是"+randInt+",请勿泄露</p>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                    "\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"margin-bottom:40px;\"><a href=\"%url%\" style=\"display:inline-block;width:139px;height:38px;line-height:38px;color:#fff;font-size:14px;vertical-align:middle;background:url(http://7xi9bi.com1.z0.glb.clouddn.com/35069/2015/07/20/0edb116f982044ba85ecd313f20e881c.jpg);text-decoration:none\">开始体验</a></div>\n" +
                    "\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"border-top:1px dashed #ccc;margin:20px\">&nbsp;</div>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"37px\" style=\"background:#dededc\">&nbsp;</td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                    "\t\t\t\t\t\t\t\t\t</table>\n" +
                    "\t\t\t\t\t\t\t\t\t</td>\n" +
                    "\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t</tbody>\n" +
                    "\t\t\t\t\t\t</table>\n" +
                    "\t\t\t\t\t\t</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t</tbody>\n" +
                    "\t\t\t</table>\n" +
                    "\t\t\t</td>\n" +
                    "\t\t</tr>\n" +
                    "\t</tbody>\n" +
                    "</table>\n" +
                    "</center>";
            logger.debug("拼接发送内容: " + sendBody);
            // 设置发信内容
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("apiUser", "freak_lin_test_i0YI6o"));
            params.add(new BasicNameValuePair("apiKey", "3B5fKXsatLvVSbcu"));
            params.add(new BasicNameValuePair("to", email));
            // 该值是我们的发件邮箱, 只能设置sendcloud上绑定的
            params.add(new BasicNameValuePair("from", "sendcloud@sendcloud.org"));
            params.add(new BasicNameValuePair("fromName", fromName));
            params.add(new BasicNameValuePair("subject", subject));
            params.add(new BasicNameValuePair("html", sendBody));

            // 初始化请求
            httpPost = new HttpPost("http://api.sendcloud.net/apiv2/mail/send");
            httpClient = HttpClients.createDefault();

            // 发送请求 将数据转换为JSON格式
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                // 接收请求
                HttpResponse response = httpClient.execute(httpPost);

                // 获取请求返回的内容值
                JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
                logger.info("返回值:"+ jsonObject);
                // 判断请求是否发送成功
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    // 判断邮件是否发送成功
                    if ((Integer)jsonObject.get("statusCode") == 200){
                        // 发送成功将随机验证码存入缓存 5分钟后过期, 通过随机码取出对应用户数据
                        logger.debug("随机验证码:" + randInt);

                        // 正常处理
                        logger.debug("发送成功~");
                        logger.debug("返回信息: " + jsonObject.toJSONString());
                        return true;
                    } else {
                        logger.debug("邮件发送失败~");
                        logger.debug("返回信息: " + jsonObject.toJSONString());
                        return false;
                    }
                } else {
                    logger.debug("请求发送失败 ~");
                    return false;
                }
                // 异常位置可以添加对应错误码
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                logger.debug("UrlEncodedFormEntity 转换失败导致发送请求失败");
                return false;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                logger.debug("httpClient.execute(httpPost) 请求接收失败");
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                logger.debug("EntityUtils.toString(response.getEntity()) 转换失败");
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug("数据库写入失败");
                return false;
            }
        }
    }

