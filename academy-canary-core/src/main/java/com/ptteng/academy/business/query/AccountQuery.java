package com.ptteng.academy.business.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: canary
 * @description: 账号查询条件
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-20 19:02
 **/

@ApiModel(value = "AccountQuery",       // 模型名称
        description = "账号查询条件",      // 描述
        parent = IQuery.class)    // 父类
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountQuery extends IQuery {
    @ApiModelProperty(notes = "角色名称", required = false)
    private Long role_id;
    @ApiModelProperty(notes = "账号名称", required = false)
    private String username;
}
