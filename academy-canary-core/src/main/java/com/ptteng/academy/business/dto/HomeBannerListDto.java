package com.ptteng.academy.business.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * description:返回的banner图
 * author:Lin
 * Date:2018/7/21
 * Time:8:59
 */

@Data
public class HomeBannerListDto {
    private String cover_plan_url; // 封面图
    private Long id; // 文章id
    private String title; // 标题
}
