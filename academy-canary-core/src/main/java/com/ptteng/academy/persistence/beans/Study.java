package com.ptteng.academy.persistence.beans;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: canary
 * @description: 学习通用属性
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 04:20
 **/

@Data
public class Study implements Serializable {
    private static final long serialVersionUID = 1291542473637070443L;
    // 主键设置主键自动增长
    @Id // 注意是 javax.persistence.Id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date create_at;
    private Date update_at;
    private String create_by;
    private String update_by;

    // 标题
    private String title;
    // 封面图
    private String cover_plan_url;
    // 作者
    private Long author;
    // 类型 视频还是文章 1:文章 2:视频
    private Integer study_type;
    // 分类 banner还是card 1:banners 2:card
    private Integer classify;
    // 简介
    private String introduce;
    // 正文
    private String content;
    // 视频地址
    private String video_url;
    // 视频时长
    private Long video_time;
    // 状态
    private Boolean status;
    // 点赞数  @Transient 说明这是一个临时字段
    @Transient
    private Long praise;
    // 收藏数
    @Transient
    private Long collect;
    // 年级
    private Integer grade;
    // 科目
    private Integer subject;

}
