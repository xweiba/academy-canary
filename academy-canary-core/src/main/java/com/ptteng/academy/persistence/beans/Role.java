package com.ptteng.academy.persistence.beans;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: canary
 * @description: 角色
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-23 00:04
 **/

@Data
public class Role implements Serializable {
    private static final long serialVersionUID = -4107991762344373001L;
    // 主键设置主键自动增长
    @Id // 注意是 javax.persistence.Id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date create_at;
    private Date update_at;
    private String create_by;
    private String update_by;

    // 角色标识 Shiro添加角色使用,如"role:admin",这个是唯一的
    private String roleTag;
    // 角色描叙(角色名)
    private String description;
}
