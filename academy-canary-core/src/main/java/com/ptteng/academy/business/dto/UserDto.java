package com.ptteng.academy.business.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @program: canary
 * @description: 用户业务层
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-19 03:11
 **/

@Data
public class UserDto {
    private Long id;
    private String nickName;
    private String email;
    private Long phone;
    private String grade;
    private String prefecture;
    private Integer bean;
    private String headImgUrl;
    private Boolean status;
    private Long signinCount;
    private Long topContinuouSignin;
    private Long lastSigninTime;
    private String signinHistory;
}
