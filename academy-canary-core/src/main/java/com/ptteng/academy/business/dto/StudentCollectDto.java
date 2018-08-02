package com.ptteng.academy.business.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * description:学生收藏信息
 * author:Lin
 * Date:2018/7/20
 * Time:19:11
 */
@Data
public class StudentCollectDto {
    //封面
    private String coverPlanUrl;
    //标题
    private String title;
    //作者
    private String author;
    private String authorImgUrl;
    //点赞数
    private long praise;
    //摘要
    private String digest;
    //收藏时间
    private long collect;
    private Date collectTime;
    private long articleId;
    private long videoId;
    private String videoUrl;
    private String content;
    private boolean praiseState;
    private boolean collectState;
    private Long video_time; // 视频时长
    private Date create_at; // 文章创建时间
}
