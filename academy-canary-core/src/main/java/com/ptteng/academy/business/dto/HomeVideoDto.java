package com.ptteng.academy.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @program: canary
 * @description: 前台文章详情Dto
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-30 00:39
 **/

@Data
public class HomeVideoDto {
    private Long id; // 查看时需要
    private String title; // 标题
    private Date create_at; // 文章创建时间
    private String authorName; // 作者名称
    private String introduce; // 摘要
    private String videoUrl; // 视频Url
    private Long videoTime; // 视频时长
    private String coverPlanUrl; // 预览图
    private String content; // 文章内容
    private Long praise; // 收藏数
    private Long collect; // 点赞数
    private Boolean praiseStatus; // 点赞状态
    private Boolean collectStatus; // 收藏状态
}
