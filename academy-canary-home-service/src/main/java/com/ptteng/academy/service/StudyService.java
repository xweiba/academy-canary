package com.ptteng.academy.service;

import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.HomeBannerListDto;
import com.ptteng.academy.business.dto.HomeVideoBannerDto;
import com.ptteng.academy.business.dto.HomeVideoDto;
import com.ptteng.academy.business.dto.HomeVideoListDto;
import com.ptteng.academy.business.query.HomeVideoQuery;

import java.util.List;

/**
 * @program: canary
 * @description: 视频文章接口
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-29 16:51
 **/

public interface StudyService {
    PageInfo<HomeVideoBannerDto> findVideoBannerByQuery(HomeVideoQuery homeVideoQuery);

    PageInfo<HomeVideoListDto> findVideosByQuery(HomeVideoQuery homeVideoQuery);

    HomeVideoDto findStudyByQuery(Long studyId, Long userId);

    Boolean updatePraiseCollectStatus(Long studyId, Long userId, Integer type);
}
