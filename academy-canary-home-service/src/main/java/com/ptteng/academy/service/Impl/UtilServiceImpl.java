package com.ptteng.academy.service.Impl;

import com.ptteng.academy.framework.config.AliyunConfig;
import com.ptteng.academy.service.UtilService;
import com.ptteng.academy.util.RandNumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: canary
 * @description:
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-04 22:47
 **/

@Slf4j
@Service
public class UtilServiceImpl implements UtilService {

    @Resource
    private AliyunConfig aliyunConfig;

    @Override
    public Boolean sendSMS(Long id, String phoneId) {
        return aliyunConfig.sendSms(id, phoneId);
    }
}
