package com.ptteng.academy.business.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: canary
 * @description: 模块
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-21 02:15
 **/

@Data
public class ModuleDto {
    private Long id;
    private String name;
    private Long parent_id;
    private String module_url;
    // 排序
    private Integer index;
    private Date create_at;
    private String create_by;
}
