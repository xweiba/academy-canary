package com.ptteng.academy.business.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * description:更新视频传入参数
 * author:Lin
 * Date:2018/7/21
 * Time:17:04
 */
@Data
public class VideoQuery {
    private int pageNum;
    private int pageSize;
    private String grade;
    private String subject;
}
