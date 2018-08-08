package com.ptteng.academy.service.Impl;

import com.ptteng.academy.business.dto.WeChatTokenDto;
import com.ptteng.academy.business.dto.WeChatUserDto;
import com.ptteng.academy.business.dto.WexinJsapiTicket;
import com.ptteng.academy.framework.exception.ResourceIsNullException;
import com.ptteng.academy.persistence.beans.User;
import com.ptteng.academy.persistence.mapper.UserMapper;
import com.ptteng.academy.service.WechatService;
import com.ptteng.academy.util.WeChatUtil;
import com.ptteng.academy.util.WechatSignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * description:
 * author:Lin
 * Date:2018/7/28
 * Time:16:39
 */

@Slf4j
@Service
public class WechatServiceImpl implements WechatService {
    @Autowired
    UserMapper userMapper;
    @Override
    public WeChatUserDto userLogin(String code) throws ResourceIsNullException {
        WeChatTokenDto weChatTokenDto = WeChatUtil.getTokenBycode(WeChatUtil.APPID, WeChatUtil.APPSECRET, code);
        String accessToken = weChatTokenDto.getAccess_token();
        String openId = weChatTokenDto.getOpenid();
        User user1 = new User();
        user1.setOpenId(openId);
        //数据库里用户的信息
        User user = userMapper.selectOne(user1);
        //返回信息
        WeChatUserDto weChatUserDto = new WeChatUserDto();
        if (user != null) {
            weChatUserDto.setOpenId(user.getOpenId());
            weChatUserDto.setId(user.getId());
            // 状态false的用户禁止访问
            weChatUserDto.setStatus(user.getStatus());
            return weChatUserDto;
        }else {
            weChatUserDto = WeChatUtil.getUserInfo(accessToken, openId);
            //新建用户
            User newUser = new User();
            newUser.setOpenId(weChatUserDto.getOpenId());
            newUser.setNickName(weChatUserDto.getNickname());
            newUser.setHeadImgUrl(weChatUserDto.getHeadImgUrl());
            newUser.setPrefecture(weChatUserDto.getCity());
            newUser.setBean(0);
            newUser.setBinding(false);
            newUser.setStatus(true);
            newUser.setCreateAt(new Date());
            newUser.setCreateBy("系统创建");
            userMapper.insert(newUser);
            /*
            //新建后用户（为了取自增的ID）
            User user2 = userMapper.selectOne(newUser);*/
            //新建一个返回值
            // weChatUserDto.setOpenId(newUser.getOpenId());
            WeChatUserDto weChatUserBack = new WeChatUserDto();
            weChatUserBack.setId(newUser.getId());
            weChatUserBack.setOpenId(weChatTokenDto.getOpenid());
            // 状态false的用户禁止访问
            weChatUserBack.setStatus(newUser.getStatus());
            return weChatUserBack;
        }
    }

    @Override
    public Map<String, String> weChatAuthority(String url) throws ResourceIsNullException {
        String jsapiTicket = WexinJsapiTicket.getJsapiTicket();
        if (jsapiTicket == null || jsapiTicket.equals("")) {
            throw new ResourceIsNullException("提醒: 后台 jsapiTicket 获取失败, 请稍后再试~");
        }
        return WechatSignUtil.getWexinAuthority(jsapiTicket, url);
    }
}