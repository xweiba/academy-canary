package com.ptteng.academy.business.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: canary
 * @description: 文章列表
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 03:45
 **/

@Data
public class ArticleListDto {
    private Long id; // 编辑时需要
    private String title; // 标题
    private String cover_plan_url; // 封面
    private String classify; // 分类
    private String author_name;  // 作者
    private String introduce; // 摘要
    private Long praise; // 收藏数
    private Long collect; // 点赞数
    private Boolean status; // 状态
    private Date update_at; // 更新时间
}
