package com.ptteng.academy.persistence.beans;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: canary
 * @description: 微信用户DO对象
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 04:06
 **/
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 8098876831027177875L;
    // 主键设置主键自动增长
    @Id // 注意是 javax.persistence.Id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createAt;
    private Date updateAt;
    private String createBy;
    private String updateBy;

    // 微信openId
    private String openId;
    // 昵称
    private String nickName;
    // 头像地址 字段与微信同步
    private String headImgUrl;
    // 年级
    private Integer grade;
    // email
    private String email;
    // 手机
    private Long phone;
    // 逆袭豆
    private Integer bean;
    // 地区
    private String prefecture;
    // 账号状态, 状态false无法访问前台页面
    private Boolean status;
    // 账号绑定状态
    private Boolean binding;
}
