package com.ptteng.academy.service;

import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.*;
import com.ptteng.academy.business.query.HomeVideoQuery;
import com.ptteng.academy.business.query.PageQuery;
import com.ptteng.academy.framework.exception.FindNullException;
import com.ptteng.academy.framework.exception.ResourceIsNullException;
import com.ptteng.academy.persistence.beans.Study;

import java.util.List;

/**
 * @program: canary
 * @description: 视频文章接口
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-29 16:51
 **/

public interface StudyService {
    PageInfo<HomeVideoBannerDto> findVideoBannerByQuery(HomeVideoQuery homeVideoQuery) throws FindNullException;

    PageInfo<HomeVideoListDto> findVideosByQuery(HomeVideoQuery homeVideoQuery) throws FindNullException;

    HomeVideoDto findStudyByQuery(Long studyId, Long userId, Integer studyType) throws ResourceIsNullException;

    Boolean updatePraiseCollectStatus(Long studyId, Long userId, Integer type);

    List<HomeBannerListDto> findArticleBanneryByQuery(Integer num) throws Exception;

    PageInfo<HomeArticleListDto> findArticleByQuery(PageQuery pageQuery) throws FindNullException;

    ArticleDetailsDto findArticleById(Long id,Long stu) throws ResourceIsNullException;
}
