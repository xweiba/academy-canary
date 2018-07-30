package com.ptteng.academy.business.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: canary
 * @description: 角色查询条件
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-21 02:10
 **/
@ApiModel(value = "RoleQuery",       // 模型名称
        description = "角色查询条件",      // 描述
        parent = IQuery.class)    // 父类
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleQuery extends IQuery{
    @ApiModelProperty(notes = "角色名称", required = false)
    private Long roleName;
}
