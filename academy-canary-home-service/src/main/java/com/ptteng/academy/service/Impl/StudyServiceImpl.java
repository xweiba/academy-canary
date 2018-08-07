package com.ptteng.academy.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.ptteng.academy.business.Converfor;
import com.ptteng.academy.business.dto.*;
import com.ptteng.academy.business.query.HomeVideoQuery;
import com.ptteng.academy.business.query.PageQuery;
import com.ptteng.academy.framework.exception.FindNullException;
import com.ptteng.academy.framework.exception.ResourceIsNullException;
import com.ptteng.academy.persistence.beans.Study;
import com.ptteng.academy.persistence.mapper.StudyMapper;
import com.ptteng.academy.service.StudyService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DataIntegrityViolationException;
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
public class StudyServiceImpl implements StudyService {
    @Resource
    private StudyMapper studyMapper;

    // 分页查询视频和Banner数据
    @Override
    public PageInfo<HomeVideoBannerDto> findVideoBannerByQuery(HomeVideoQuery homeVideoQuery) throws FindNullException {
        // 设置分页条件
        PageHelper.startPage(homeVideoQuery.getPageNum(), homeVideoQuery.getPageSize());
        List<HomeVideoListDto> homeVideoListDtoList = studyMapper.findVideoByVideoQuery(homeVideoQuery);
        PageInfo bean = new PageInfo();

        HomeVideoBannerDto homeVideoBannerDto = new HomeVideoBannerDto();

        // Banner 只输出8条
        homeVideoQuery.setPageSize(8);
        List<HomeBannerListDto> homeBannerListDtos = studyMapper.findBannerByQuery(homeVideoQuery);
        if (!homeBannerListDtos.isEmpty()) {
            bean = new PageInfo<>(homeBannerListDtos);
            homeVideoBannerDto.setBanners(homeBannerListDtos);
        }

        // 注意这里会刷新bean的total和rows值, 当homeBannerListDtos不为空时bean会覆盖之前的信息, 这里需要视频的列表信息, 所以必须放在banner后面
        if (!homeVideoListDtoList.isEmpty()) {
            bean = new PageInfo<>(homeVideoListDtoList);
            homeVideoBannerDto.setCards(homeVideoListDtoList);
        }

        if (homeVideoListDtoList.isEmpty()) {
            if (homeBannerListDtos.isEmpty()) {
                throw new FindNullException("提醒: 没有视频和Banner数据了, 快去后台添加吧~");
            }
        }

        List<HomeVideoBannerDto> objectList = new ArrayList<>();

        objectList.add(homeVideoBannerDto);
        bean.setList(objectList);
        return bean;
    }

    // 下拉刷新获取视频数据
    @Override
    public PageInfo<HomeVideoListDto> findVideosByQuery(HomeVideoQuery homeVideoQuery) throws FindNullException {
        // 设置分页条件
        PageHelper.startPage(homeVideoQuery.getPageNum(), homeVideoQuery.getPageSize());
        List<HomeVideoListDto> homeVideoListDtoList = studyMapper.findVideoByVideoQuery(homeVideoQuery);
        log.debug("下拉刷新获取视频数据:" + JSONObject.toJSONString(homeVideoListDtoList));
        if (homeVideoListDtoList.isEmpty()) {
            throw new FindNullException("提醒: 没有视频card文章啦~ 赶紧去后台添加吧~");
        }
        return new PageInfo<>(homeVideoListDtoList);
    }

    // 获取视频文章详细信息
    @Override
    public HomeVideoDto findStudyByQuery(Long studyId, Long userId, Integer studyType) throws ResourceIsNullException {
        HomeVideoDto homeVideoDto = studyMapper.findStudyByQuery(studyId, userId, studyType);
        if (homeVideoDto == null) {
            throw new ResourceIsNullException("提醒: 该视频文章不存在~");
        }
        return homeVideoDto;
    }

    @Override
    public Boolean updatePraiseCollectStatus(Long studyId, Long userId, Integer type) {
        log.info("传入参数: " + studyId + userId + type);
        // 1. 判断是否有点赞过, 有直接删除点赞记录
        if (studyMapper.findPraiseCollectStatus(studyId, userId, type)) {
            studyMapper.deletePraiseCollectStatus(studyId, userId, type);
            return false;
        }
        // 2. 不存在插入记录.
        try {
            return studyMapper.insertPraiseCollectStatus(studyId, userId, type, new Date());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("提醒: 点赞/收藏失败, 用户或文章id不存在");
        }
    }

    //查询banner文章
    @Override
    public List<HomeBannerListDto> findArticleBanneryByQuery(Integer num) throws FindNullException, ResourceIsNullException {
        /*if (num == null) {
            throw new ResourceIsNullException("提醒: num 值不能为0或为空");
        }*/
        List<HomeBannerListDto> homeBannerListDtoList = studyMapper.findBannerArticleByQuery(num);
        if (homeBannerListDtoList.isEmpty()) {
            throw new FindNullException("提醒: Banner 文章无数据~");
        }
        return homeBannerListDtoList;
    }

    //查询card文章列表
    @Override
    public PageInfo<HomeArticleListDto> findArticleByQuery(PageQuery pageQuery) throws FindNullException {
        // 设置分页条件
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<HomeArticleListDto> homeArticleListDtoList = studyMapper.findArticleByQuery();
        if (homeArticleListDtoList.isEmpty()) {
            throw new FindNullException("提醒: card文章无数据~");
        }
        for (HomeArticleListDto homeArticleListDto : homeArticleListDtoList) {
            homeArticleListDto.setPraisestu(studyMapper.findPraiseCollectStatus(homeArticleListDto.getId(), pageQuery.getUserId(), 1));
            homeArticleListDto.setCollectstu(studyMapper.findPraiseCollectStatus(homeArticleListDto.getId(), pageQuery.getUserId(), 2));
        }
        PageInfo<HomeArticleListDto> bean = new PageInfo<>(homeArticleListDtoList);
        bean.setList(homeArticleListDtoList);
        log.info("页面信息：" + bean);
        return bean;
    }

    //查看文章详情
    @Override
    public ArticleDetailsDto findArticleById(Long id, Long stu) throws ResourceIsNullException {
        ArticleDetailsDto study = studyMapper.findCardArticleByQuery(id, stu);
        if (study == null) {
            throw new ResourceIsNullException("提醒: 该文章不存在或已下架");
        }
        return study;
    }
}
