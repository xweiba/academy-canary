package com.ptteng.academy.business.dto;

import lombok.Data;

import java.util.Date;

/**
 * description:
 * author:Lin
 * Date:2018/8/2
 * Time:10:40
 */
@Data
public class StudentCollectArticleDto {
    //文章id
    private Long id;
    //封面
    private String coverPlanUrl;
    //标题
    private String title;
    //作者
    private String author;
    //点赞数
    private long praise;
    //摘要
    private String digest;
    //收藏数
    private long collect;
    //收藏时间
    private Date createTime;
}
