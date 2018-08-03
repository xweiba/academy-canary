package com.ptteng.academy.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.ptteng.academy.business.dto.WeChatTokenDto;
import com.ptteng.academy.business.dto.WeChatUserDto;
import com.ptteng.academy.persistence.beans.User;
import com.ptteng.academy.persistence.mapper.UserMapper;
import com.ptteng.academy.service.WechatService;
import com.ptteng.academy.util.WeChatUtil;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/**
 * description:
 * author:Lin
 * Date:2018/7/28
 * Time:16:39
 */

@Service
public class WechatServiceImpl implements WechatService {
    private static final Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);
    @Autowired
    UserMapper userMapper;
    @Override
    public WeChatUserDto userLogin(String code) {
        WeChatTokenDto weChatTokenDto = WeChatUtil.getTokenBycode(WeChatUtil.APPID, WeChatUtil.APPSECRET, code);
        String accessToken = weChatTokenDto.getAccess_token();
        String openId = weChatTokenDto.getOpenid();
        User user1 = new User();
        user1.setOpenId(openId);
        //数据库里用户的信息
        User user = userMapper.selectOne(user1);
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
            newUser.setBinding(false);
            newUser.setStatus(true);
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            newUser.setCreateAt(timestamp);
            newUser.setCreateBy("用户");
            userMapper.insert(newUser);
            //新建后用户（为了取自增的ID）
            User user2 = userMapper.selectOne(newUser);
            //新建一个返回值
            UserDto.setOpenId(user2.getOpenId());
            UserDto.setId(user2.getId());
            return UserDto;
        }
    }
}
