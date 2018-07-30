package com.ptteng.academy.service.Impl;

import com.ptteng.academy.business.dto.WeChatTokenDto;
import com.ptteng.academy.business.dto.WeChatUserDto;
import com.ptteng.academy.persistence.beans.User;
import com.ptteng.academy.persistence.mapper.UserMapper;
import com.ptteng.academy.service.WechatService;
import com.ptteng.academy.util.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description:
 * author:Lin
 * Date:2018/7/28
 * Time:16:39
 */

@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    UserMapper userMapper;
    @Override
    public WeChatUserDto userLogin(String code) {
        WeChatTokenDto weChatTokenDto = WeChatUtil.getTokenBycode(WeChatUtil.APPID, WeChatUtil.APPSECRET, code);
        String accessToken = weChatTokenDto.getAccessToken();
        String openId = weChatTokenDto.getOpenid();
        //数据库里用户的信息
        User user = userMapper.selectByPrimaryKey(openId);
        //返回信息
        WeChatUserDto UserDto = new WeChatUserDto();
        if (user != null) {
            UserDto.setOpenId(user.getOpenId());
            UserDto.setId(user.getId());
            return UserDto;
        }else {
            WeChatUserDto weChatUserDto = WeChatUtil.getUserInfo(accessToken, openId);
            //新建用户
            User newUser = new User();
            newUser.setOpenId(weChatUserDto.getOpenId());
            newUser.setNickName(weChatUserDto.getNickname());
            newUser.setHeadImgUrl(weChatUserDto.getHeadImgUrl());
            newUser.setPrefecture(weChatUserDto.getCity());
            newUser.setBean(0);
            newUser.setBinding(true);
            newUser.setStatus(true);
            userMapper.insert(newUser);
            //新建后用户（为了取自增的ID）
            User user1 = userMapper.selectByPrimaryKey(weChatUserDto.getOpenId());
            //新建一个返回值
            WeChatUserDto weChatUserDto1 = new WeChatUserDto();
            weChatUserDto1.setOpenId(weChatUserDto.getOpenId());
            weChatUserDto1.setId(user1.getId());
            return weChatUserDto1;
        }
    }
}
