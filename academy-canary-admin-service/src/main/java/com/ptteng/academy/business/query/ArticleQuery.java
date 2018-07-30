package com.ptteng.academy.business.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: canary
 * @description: 文章查询条件
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 03:39
 **/

@ApiModel(value = "ArticleQuery",       // 模型名称
        description = "文章查询条件",      // 描述
        parent = IQuery.class)    // 父类
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleQuery extends IQuery{
    @ApiModelProperty(notes = "标题", required = false)
    private String title;
    @ApiModelProperty(notes = "作者id", required = false)
    private String author;
    @ApiModelProperty(notes = "文章类型id,1:banners 2:card", required = false)
    private Integer classify;
    @ApiModelProperty(notes = "点赞范围", required = false, dataType="Integer[]")
    private Integer[] praise;
    @ApiModelProperty(notes = "收藏范围", required = false, dataType="Integer[]")
    private Integer[] collect;
    // 文章类型
    @JsonIgnore
    private final Integer study_type = 1;
}
