package com.ptteng.academy.business.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: canary
 * @description: 视频列表Dto
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-29 16:53
 **/

@Data
public class HomeVideoListDto {
    private Long id; // 查看时需要
    private String title; // 标题
    private String authorImg;  // 作者图像
    private String authorName; // 作者名称
    private Long praise; // 收藏数
    private Long collect; // 点赞数
    private Date createAt; // 文章创建时间
    private String introduce; // 文章简介
    private String coverPlanUrl; // 封面图
    private Long videoTime; // 视频时长
}
