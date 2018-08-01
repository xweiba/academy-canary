package com.ptteng.academy.business.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: canary
 * @description: 角色业务对象
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-21 01:41
 **/

@Data
public class RoleDto {
    private Long id;
    private String role_name;
    private String role_tag;
    private List<Long> moduleIds;
    private Date create_at;
    private String create_by;
}
