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
    private String author_img;  // 作者图像
    private String author_name; // 作者名称
    private Long praise; // 收藏数
    private Long collect; // 点赞数
    private Date create_at; // 文章创建时间
    private String introduce; // 文章简介
    private String cover_plan_url; // 封面图
    private Data video_time; // 视频时长
}
