package com.ptteng.academy.persistence.beans;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: canary
 * @description: 模块
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-23 00:11
 **/
@Data
public class Module implements Serializable {
    private static final long serialVersionUID = 8305610122907431999L;
    // 主键设置主键自动增长
    @Id // 注意是 javax.persistence.Id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date create_at;
    private Date update_at;
    private String create_by;
    private String update_by;

    // 模块名称 内容管理 用户列表
    private String name;
    // 模块对应的url
    private String module_url;
    // 父级id, 定位自己属于哪一个模块
    private Long parent_id;
}
