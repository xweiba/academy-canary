package com.ptteng.academy.persistence.beans;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: canary
 * @description: 账号DO对象
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 01:39
 **/

@Data
public class Account implements Serializable {
    private static final long serialVersionUID = -1033917396514108241L;
    // 主键设置主键自动增长
    @Id // 注意是 javax.persistence.Id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date create_at;
    private Date update_at;
    private String create_by;
    private String update_by;
    private String username;
    private String password;
}
