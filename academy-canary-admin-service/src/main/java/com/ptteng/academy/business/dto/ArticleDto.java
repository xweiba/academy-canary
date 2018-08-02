package com.ptteng.academy.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @program: canary
 * @description: 文章详情
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-28 23:21
 **/

@Data
public class ArticleDto {
    private Long id; // 编辑时需要
    private String title; // 标题
    private String cover_plan_url; // 封面
    private Integer classify; // 分类
    private String author;  // 作者
    private String introduce; // 摘要
    private String content; // 正文
    @JsonIgnore
    private final Integer study_type = 1;
    // 默认状态
    private Boolean status = false;
}
