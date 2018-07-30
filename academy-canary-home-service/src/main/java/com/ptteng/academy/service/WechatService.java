package com.ptteng.academy.service;

import com.ptteng.academy.business.dto.WeChatUserDto;

/**
 * description:
 * author:Lin
 * Date:2018/7/28
 * Time:16:36
 */
public interface WechatService {
    //用户登陆
    public WeChatUserDto userLogin(String code);

}
