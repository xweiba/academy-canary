package com.ptteng.academy.business.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: canary
 * @description: 视频列表数据
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-28 21:29
 **/
@Data
public class VideoListDto {
    private Long id; // 编辑时需要
    private String title; // 标题
    private String classify; // 分类
    private String author_name;  // 作者
    private Long praise; // 收藏数
    private Long collect; // 点赞数
    private Boolean status; // 状态
    private String grade; // 年级
    private String subject; // 科目
    private Date update_at; // 更新时间
}
