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
    private String coverPlanUrl;
    private String title;
    private String author;
    private String authorImgUrl;
    private long praise;
    private String  digest;
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
