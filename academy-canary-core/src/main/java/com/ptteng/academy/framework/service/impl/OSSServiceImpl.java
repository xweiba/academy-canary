package com.ptteng.academy.framework.service.impl;

import com.ptteng.academy.framework.config.QiNiuYun;
import com.ptteng.academy.framework.service.OSSService;
import com.ptteng.academy.util.MD5Util;
import com.ptteng.academy.util.UpdateFileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @program: canary
 * @description: 对象存储实现
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-28 00:11
 **/
@Service
public class OSSServiceImpl implements OSSService{
    @Resource
    QiNiuYun qiNiuYun;
    @Override
    public String updateFile(MultipartFile multipartFile) throws IOException {
        // 获取文件的后缀名
        String suffixName = UpdateFileUtil.getFileExt(multipartFile.getContentType());
        String file_name = MD5Util.getMultipartFileMd5(multipartFile) + suffixName;
        return qiNiuYun.updateMultipartFile(multipartFile, file_name);
    }

    @Override
    public String updateFile(String fileName) throws FileNotFoundException {
        return qiNiuYun.updateFileName(fileName);
    }

    @Override
    public Boolean deleteFile(String file_name) {
        return qiNiuYun.deleteFile(file_name);
    }
}
