package com.ptteng.academy.business.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: canary
 * @description: 视频查询条件
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-29 17:18
 **/

@Data
@EqualsAndHashCode(callSuper = false)
public class HomeVideoQuery extends IQuery{
    @ApiModelProperty(notes = "文章类型id,1:banners 2:card", required = false)
    private Integer classify;
    @ApiModelProperty(notes = "年级id, 0代表查询所有(不加该参数), 1-6对应对应年级", required = false)
    private Integer grade; // 年级
    @ApiModelProperty(notes = "学科id, 0代表查询所有, 1-6对应对应科目 ", required = false)
    private Integer subject; // 科目

    @ApiModelProperty(notes = "用户id", required = true)
    private Long stu_id; // 用户id

    @JsonIgnore
    private final Integer study_type = 2; // 文章类型 视频/文章
}
