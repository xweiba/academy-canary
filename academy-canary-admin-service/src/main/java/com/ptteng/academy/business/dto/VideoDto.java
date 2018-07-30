package com.ptteng.academy.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @program: canary
 * @description: 视频详情
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-28 23:31
 **/
@Data
public class VideoDto {
    private Long id; // 编辑时需要
    private Integer grade; // 年级
    private Integer subject; // 科目
    private Integer author;  // 作者
    private String title; // 标题
    private String cover_plan_url; // 封面, 视频预览图
    private Integer classify; // 分类
    private String introduce; // 摘要
    private String content; // 正文
    private String video_url; // 视频Url
    @JsonIgnore
    private final Integer study_type = 2;
}
