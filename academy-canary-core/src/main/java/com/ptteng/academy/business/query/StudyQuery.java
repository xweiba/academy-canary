package com.ptteng.academy.business.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: canary
 * @description: 视频文章查询条件
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 03:50
 **/

@ApiModel(value = "VideoQuery",       // 模型名称
        description = "视频文章查询条件",      // 描述
        parent = IQuery.class)    // 父类
@Data
@EqualsAndHashCode(callSuper = false)
public class StudyQuery extends IQuery{
    @ApiModelProperty(notes = "标题", required = false)
    private String title;
    @ApiModelProperty(notes = "文章类型id,1:banners 2:card", required = false)
    private Integer classify;
    @ApiModelProperty(notes = "年级id, 0代表查询所有(不加该参数), 1-6对应对应年级", required = false)
    private Integer grade; // 年级
    @ApiModelProperty(notes = "学科id, 0代表查询所有, 1-6对应对应科目 ", required = false)
    private Integer subject; // 科目
    @ApiModelProperty(notes = "点赞范围", required = false, dataType="Integer[]")
    private Integer[] praise;
    @ApiModelProperty(notes = "收藏范围", required = false, dataType="Integer[]")
    private Integer[] collect;
    @ApiModelProperty(notes = "作者姓名", required = false)
    private String author;
    @ApiModelProperty(notes = "下架/上架状态", required = false)
    private Boolean status;
    // 视频类型
    @JsonIgnore
    private Integer study_type;

    public StudyQuery(String title, Integer classify, Integer grade, Integer subject, Integer[] praise, Integer[] collect, String author, Boolean status) {
        this.title = title;
        this.classify = classify;
        this.grade = grade;
        this.subject = subject;
        this.praise = praise;
        this.collect = collect;
        this.author = author;
        this.status = status;
    }
    public StudyQuery() {
        super();
    }
}
