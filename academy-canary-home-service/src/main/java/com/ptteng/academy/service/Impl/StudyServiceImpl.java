package com.ptteng.academy.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.Converfor;
import com.ptteng.academy.business.dto.*;
import com.ptteng.academy.business.query.HomeVideoQuery;
import com.ptteng.academy.business.query.PageQuery;
import com.ptteng.academy.persistence.beans.Study;
import com.ptteng.academy.persistence.mapper.StudyMapper;
import com.ptteng.academy.service.StudyService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: canary
 * @description: 视频文章接口实现
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-29 17:25
 **/
@Slf4j
@Service
public class StudyServiceImpl implements StudyService{
    @Resource
    private StudyMapper studyMapper;

    // 分页查询视频和Banner数据
    @Override
    public PageInfo<HomeVideoBannerDto> findVideoBannerByQuery(HomeVideoQuery homeVideoQuery) {
        // 设置分页条件
        PageHelper.startPage(homeVideoQuery.getPageNum(), homeVideoQuery.getPageSize());
        List<HomeVideoListDto> homeVideoListDtoList = studyMapper.findVideoByVideoQuery(homeVideoQuery);
        log.info(homeVideoListDtoList.get(0).toString());
        PageInfo bean = new PageInfo<HomeVideoListDto>(homeVideoListDtoList);
        log.info("欢欢的："+bean);

        // Banner 只输出8条, 注意这里会刷新bean的total和rows值
        homeVideoQuery.setPageSize(8);
        List<HomeBannerListDto> homeBannerListDtos = studyMapper.findBannerByQuery(homeVideoQuery);

        if (homeVideoListDtoList.isEmpty()) {
            if (homeBannerListDtos.isEmpty()) {
                return null;
            }
        }

        List<HomeVideoBannerDto> objectList = new ArrayList<>();

        HomeVideoBannerDto homeVideoBannerDto = new HomeVideoBannerDto();
        homeVideoBannerDto.setBanners(homeBannerListDtos);
        homeVideoBannerDto.setCards(homeVideoListDtoList);

        objectList.add(homeVideoBannerDto);
        bean.setList(objectList);
        return bean;
    }

    // 下拉刷新获取视频数据
    @Override
    public PageInfo<HomeVideoListDto> findVideosByQuery(HomeVideoQuery homeVideoQuery) {
        // 设置分页条件
        PageHelper.startPage(homeVideoQuery.getPageNum(), homeVideoQuery.getPageSize());
        List<HomeVideoListDto> homeVideoListDtoList = studyMapper.findVideoByVideoQuery(homeVideoQuery);
        return new PageInfo<HomeVideoListDto>(homeVideoListDtoList);
    }
    // 获取视频文章详细信息
    @Override
    public HomeVideoDto findStudyByQuery(Long studyId, Long userId, Integer studyType) {
        return studyMapper.findStudyByQuery(studyId, userId, studyType);
    }

    @Override
    public Boolean updatePraiseCollectStatus(Long studyId, Long userId, Integer type) {
        log.info("传入参数: " + studyId + userId + type);
        // 1. 判断是否有点赞过, 有直接删除点赞记录
        if (studyMapper.findPraiseCollectStatus(studyId,userId,type)) {
            return studyMapper.deletePraiseCollectStatus(studyId,userId,type);
        }
        // 2. 不存在插入记录.
        return studyMapper.insertPraiseCollectStatus(studyId,userId,type,new Date());
    }
//查询banner文章
    @Override
    public List<HomeBannerListDto> findArticleBanneryByQuery(Integer num) {
        return studyMapper.findBannerArticleByQuery(num);
    }

    //查询card文章列表
    @Override
    public PageInfo<HomeArticleListDto> findArticleByQuery(PageQuery pageQuery) {
        // 设置分页条件
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        Study study = new Study();
        study.setClassify(2);
        study.setStudy_type(1);
        List<HomeArticleListDto> homeArticleListDtoList = studyMapper.findArticleByQuery();
        for (HomeArticleListDto homeArticleListDto : homeArticleListDtoList) {
            homeArticleListDto.setPraisestu(studyMapper.findPraiseCollectStatus(homeArticleListDto.getId(),pageQuery.getUserId(),1));
            homeArticleListDto.setCollectstu(studyMapper.findPraiseCollectStatus(homeArticleListDto.getId(),pageQuery.getUserId(),2));
        }
        PageInfo bean = new PageInfo<HomeArticleListDto>(homeArticleListDtoList);
        bean.setList(homeArticleListDtoList);
        log.info("页面信息："+bean);
        return bean;
    }

    //查看文章详情
    @Override
    public ArticleDetailsDto findArticleById(Long id,Long stu) {
        Study study = studyMapper.selectByPrimaryKey(id);
        ArticleDetailsDto articleDetailsDto = Converfor.StudytorticleDetailsDto(study);
        articleDetailsDto.setPraisestu(studyMapper.findPraiseCollectStatus(id,stu,1));
        articleDetailsDto.setCollectstu(studyMapper.findPraiseCollectStatus(id,stu,2));
        return articleDetailsDto;
    }
}
