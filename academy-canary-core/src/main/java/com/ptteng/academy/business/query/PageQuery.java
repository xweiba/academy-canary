package com.ptteng.academy.business.query;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * description:返回的页数信息
 * author:Lin
 * Date:2018/7/21
 * Time:10:39
 */
@ApiModel(value = "RoleQuery",       // 模型名称
        description = "角色查询条件",      // 描述
        parent = IQuery.class)    // 父类
@Data
@EqualsAndHashCode(callSuper = false)
public class PageQuery extends IQuery{
    private Long userId;
}
