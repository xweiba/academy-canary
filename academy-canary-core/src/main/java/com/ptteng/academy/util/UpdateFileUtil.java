package com.ptteng.academy.util;

/**
 * @program: canary
 * @description: 上传文件工具类
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-19 22:51
 **/

public class UpdateFileUtil {
    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType 内容类型
     * @return
     */
    public static String getFileExt(String contentType) {
        String fileExt = "";
        if ("image/jpeg".equals(contentType))
            fileExt = ".jpg";
        else if("image/png".equals(contentType))
            fileExt = ".png";
        else if ("audio/mpeg".equals(contentType))
            fileExt = ".mp3";
        else if ("audio/amr".equals(contentType))
            fileExt = ".amr";
        else if ("video/mp4".equals(contentType))
            fileExt = ".mp4";
        else if ("video/mpeg4".equals(contentType))
            fileExt = ".mp4";
        return fileExt;
    }
}
