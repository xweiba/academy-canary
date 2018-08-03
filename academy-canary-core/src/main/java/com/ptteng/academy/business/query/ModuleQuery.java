package com.ptteng.academy.business.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: canary
 * @description: 模块查询条件
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-21 02:21
 **/

@ApiModel(value = "ModuleQuery",       // 模型名称
        description = "模块查询条件",      // 描述
        parent = IQuery.class)    // 父类
@Data
@EqualsAndHashCode(callSuper = false)
public class ModuleQuery extends IQuery{
    @ApiModelProperty(notes = "模块名称", required = false)
    private String moduleName;
    @JsonIgnore
    private Long role_id;
}
