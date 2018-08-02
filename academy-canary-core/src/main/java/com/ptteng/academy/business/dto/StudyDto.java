package com.ptteng.academy.business.dto;


import lombok.Data;

import java.util.Date;

/**
 * @program: canary
 * @description: 视频文章业务数据
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 03:52
 **/

@Data
public class StudyDto {
    private Long id; // 编辑时需要
    private Integer study_type;
    private String title; // 标题
    private String cover_plan_url; // 封面
    private Integer classify; // 分类
    private Long author;  // 作者
    private String author_img; // 作者头像
    private String author_name; // 作者名称
    private String introduce; // 摘要
    private Long praise; // 收藏数
    private Long collect; // 点赞数
    private Boolean status; // 状态
    private String content; // 文章内容
    private Integer grade; // 年级
    private Integer subject; // 科目
    private Long video_time; // 视频时长
    private String video_url; // 视频Url
    private Date update_at; // 更新时间
    private Date create_at; // 创建时间
}