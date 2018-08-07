package com.ptteng.academy.service;

import com.ptteng.academy.business.dto.WeChatUserDto;
import com.ptteng.academy.framework.exception.ResourceIsNullException;

import java.util.Map;

/**
 * description:
 * author:Lin
 * Date:2018/7/28
 * Time:16:36
 */
public interface WechatService {
    // 用户登陆
    WeChatUserDto userLogin(String code) throws ResourceIsNullException;
    // 获取微信接口权限
    Map<String, String> weChatAuthority(String url) throws ResourceIsNullException;
}
