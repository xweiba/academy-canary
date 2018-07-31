package com.ptteng.academy.service;

import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.ArticleDto;
import com.ptteng.academy.business.dto.StudyDto;
import com.ptteng.academy.business.dto.VideoDto;
import com.ptteng.academy.business.query.StudyQuery;
import com.ptteng.academy.framework.pojo.AbstractService;

/**
 * @program: canary
 * @description: 视频和文章
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-26 19:08
 **/

public interface StudyService extends AbstractService<StudyDto, Long>{
    PageInfo<?> findPageBreakByCondition(Object objectQuery) throws Exception;

    Boolean updateStatusById(Long id) throws Exception;
    // 更新文章
    Boolean updateByArticle(ArticleDto articleDto);

    ArticleDto findArticleById(Long id) throws Exception;
    VideoDto findVideoById(Long id) throws Exception;

    Long insertVideo(VideoDto videoDto) throws Exception;
    Long insertArticle(ArticleDto articleDto) throws Exception;

}
