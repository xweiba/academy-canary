package com.ptteng.academy.business.dto;

import lombok.Data;

/**
 * description:
 * author:Lin
 * Date:2018/8/1
 * Time:15:27
 */
@Data
public class HomeArticleListDto {
    private Long id; // 查看时需要
    private String title; // 标题
    private String authorName;//作者
    private String introduce;//摘要
    private Integer praise;//点赞数
    private Integer collect;//收藏数
    private java.util.Date createAt;//创建时间
    private String coverPlanUrl;//封面图
    private boolean praisestu;//点赞状态
    private boolean collectstu;//收藏状态
}
