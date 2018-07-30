package com.ptteng.academy.business.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: canary
 * @description: 通用查询条件
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 03:40
 **/

@ApiModel(value = "IQuery",       // 模型名称
        description = "通用查询条件")      // 描述
@Data
abstract class IQuery {
    @ApiModelProperty(notes = "状态", required = false)
    private Long id;
    @ApiModelProperty(notes = "每页数量", required = false)
    private Integer pageSize = 10;
    @ApiModelProperty(notes = "页数", required = false)
    private Integer pageNum = 1;
}
