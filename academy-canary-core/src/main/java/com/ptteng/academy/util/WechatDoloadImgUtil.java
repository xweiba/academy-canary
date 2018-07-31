package com.ptteng.academy.util;

import com.ptteng.academy.business.dto.WeixinAccessToken;
import com.ptteng.academy.framework.config.QiNiuYun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * description:
 * author:Lin
 * Date:2018/7/30
 * Time:18:11
 */
public class WechatDoloadImgUtil {


    private static final Logger logger = LoggerFactory.getLogger(WechatDoloadImgUtil.class);
    public static String downloadMedia (String accessToken, String mediaId) {
        try {
            accessToken = WeixinAccessToken.getAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String fileUrl = null;
        // 拼接请求地址
        String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            // 根据内容类型获取扩展名
            String fileExt = UpdateFileUtil.getFileExt(conn.getHeaderField("Content-Type"));
            // 将mediaId作为文件名
            String fileName = mediaId + fileExt;
            InputStream bis = conn.getInputStream();
            QiNiuYun qiNiuYun = new QiNiuYun();
            fileUrl = qiNiuYun.updateFileReal(bis, fileName);
            bis.close();
            conn.disconnect();
            logger.info("上传文件成功，fileName=" + fileName);
        } catch (Exception e) {
            String error = String.format("上传文件失败：%s", e);
            logger.error(error);
        }
        return fileUrl;
    }
}

