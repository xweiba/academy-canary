package com.ptteng.academy.business.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: canary
 * @description: 微信Token
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-19 22:33
 **/

@Data
public class WeChatTokenDto {
    // 接口访问凭证
    private String accessToken;
    // 凭证有效期，单位：秒
    private int expiresIn;
    //用户刷新accessToken
    private String refresh_token;
    //用户唯一标识
    private String openid;
    //用户授权的作用域
    private String scope;
}
