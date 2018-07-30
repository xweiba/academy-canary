package com.ptteng.academy.framework.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @program: canary
 * @description: 对象存储OSS
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-28 00:10
 **/

public interface OSSService {
    String updateFile(MultipartFile multipartFile) throws IOException;
    String updateFile(String fileName) throws FileNotFoundException;
    Boolean deleteFile(String file_name);
}
