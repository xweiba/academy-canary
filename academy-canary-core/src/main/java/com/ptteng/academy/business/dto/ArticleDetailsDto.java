package com.ptteng.academy.business.dto;

import lombok.Data;

/**
 * description:
 * author:Lin
 * Date:2018/8/1
 * Time:17:05
 */
@Data
public class ArticleDetailsDto {
    private String title; // 标题
    private String authorName;//作者
    private Integer praise;//点赞数
    private Integer collect;//收藏数
    private java.util.Date createAt;//创建时间
    private String  content;//正文内容
    private boolean praiseStatus;//点赞状态
    private boolean collectStatus;//收藏状态
}
