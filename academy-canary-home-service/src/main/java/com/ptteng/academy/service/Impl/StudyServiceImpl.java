package com.ptteng.academy.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.HomeBannerListDto;
import com.ptteng.academy.business.dto.HomeVideoBannerDto;
import com.ptteng.academy.business.dto.HomeVideoDto;
import com.ptteng.academy.business.dto.HomeVideoListDto;
import com.ptteng.academy.business.query.HomeVideoQuery;
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

    @Override
    public HomeVideoDto findStudyByQuery(Long studyId, Long userId) {
        return studyMapper.findStudyByQuery(studyId, userId);
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
}
