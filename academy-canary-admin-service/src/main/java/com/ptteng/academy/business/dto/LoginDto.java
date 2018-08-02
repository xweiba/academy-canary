package com.ptteng.academy.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @program: canary
 * @description: 登陆业务层对象
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 01:29
 **/

@Data
public class LoginDto {
    // 用户do
    private String accountName;
    private String passWord;
    private String roleName;
    private Long id;
    // 是否勾选记住我
    private Boolean rememberMe;
}
