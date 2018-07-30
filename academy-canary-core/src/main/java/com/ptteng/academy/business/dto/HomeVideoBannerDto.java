package com.ptteng.academy.business.dto;

import lombok.Data;

import java.util.List;

/**
 * @program: canary
 * @description: 视频和bannerDto
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-29 17:15
 **/
@Data
public class HomeVideoBannerDto  {
    private List<HomeBannerListDto> banners;
    private List<HomeVideoListDto> cards;
}
