package com.ptteng.academy.business.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * description:文章和视频的信息
 * author:Lin
 * Date:2018/7/21
 * Time:10:51
 */
@Data
public class ArticleAndVideoDto {
    private String title;
    private java.util.Date updateTime;
    private String author;
    private String content;
    private String videoUrl;
    private int praise;
    private int collect;
    private boolean praiseStu;
    private boolean collectStu;
}
