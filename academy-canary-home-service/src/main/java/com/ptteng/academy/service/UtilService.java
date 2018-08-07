package com.ptteng.academy.service;

import com.aliyuncs.exceptions.ClientException;
import com.ptteng.academy.framework.exception.ResourceIsNullException;

/**
 * @program: canary
 * @description: 工具类服务
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-04 22:46
 **/

public interface UtilService {
    Boolean sendSMS(Long id, String phoneId) throws ClientException, ResourceIsNullException;
}
