package com.ptteng.academy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.*;
import com.ptteng.academy.business.enums.ClassifyEnum;
import com.ptteng.academy.business.enums.GradeEnum;
import com.ptteng.academy.business.enums.SubjectEnum;
import com.ptteng.academy.business.query.StudyQuery;
import com.ptteng.academy.framework.exception.FindNullException;
import com.ptteng.academy.framework.exception.ResourceIsNullException;
import com.ptteng.academy.framework.service.OSSService;
import com.ptteng.academy.persistence.beans.Study;
import com.ptteng.academy.persistence.mapper.StudyMapper;
import com.ptteng.academy.service.ConsumeService;
import com.ptteng.academy.service.ManageService;
import com.ptteng.academy.service.StudyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: canary
 * @description: 视频和文章
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-26 19:45
 **/

@Slf4j
@Service
public class StudyServiceImpl implements StudyService {
    @Resource
    private StudyMapper studyMapper;

    @Resource
    private ConsumeService consumeService;

    // 文件上传
    @Resource
    private OSSService ossService;

    @Resource
    private ManageService manageService;

    @Override
    public StudyDto insert(StudyDto entity) throws Exception {
        /* 上传图片到OSS */
        if (!"".equals(entity.getCover_plan_url()) && entity.getCover_plan_url() != null) {
            entity.setCover_plan_url(ossService.updateFile(entity.getCover_plan_url()));
        }
        Study study = new Study();
        BeanUtils.copyProperties(entity, study);
        study.setCreate_at(new Date());
        study.setUpdate_at(new Date());
        study.setCreate_by(manageService.getOnlineAccount().getUsername());
        study.setUpdate_by(manageService.getOnlineAccount().getUsername());
        studyMapper.insert(study);
        return entity;
    }

    @Override
    public void insertList(List<StudyDto> entities) throws Exception {

    }

    @Override
    public boolean removeByPrimaryKey(Long primaryKey) throws Exception {
        return false;
    }

    @Override
    public boolean update(StudyDto entity) throws Exception {
        return false;
    }


    // 更新不为空的字段
    @Override
    public boolean updateByPrimaryKeySelective(StudyDto entity) throws Exception {
        log.debug("entity.toString()更新信息:" + entity.toString());
        /* 上传图片到OSS */
        if (!"".equals(entity.getCover_plan_url()) && entity.getCover_plan_url() != null) {
            log.debug("开始上传到OSS");
            entity.setCover_plan_url(ossService.updateFile(entity.getCover_plan_url()));
        }
        Study study = new Study();
        BeanUtils.copyProperties(entity, study);
        // 更新时间
        study.setUpdate_at(new Date());
        study.setUpdate_by(manageService.getOnlineAccount().getUsername());
        study.setStudy_type(2);
        return studyMapper.updateByPrimaryKeySelective(study) > 0;
    }

    @Override
    public StudyDto getByPrimaryKey(Long primaryKey) throws Exception {
        return null;
    }

    @Override
    public StudyDto getOneByEntity(StudyDto entity) throws Exception {
        return null;
    }

    @Override
    public List<StudyDto> listAll() throws Exception {
        return null;
    }

    @Override
    public List<StudyDto> listByEntity(StudyDto entity) throws Exception {
        return null;
    }

    // 分页查询
    @Override
    public PageInfo<?> findPageBreakByCondition(Object objectQuery) throws Exception {
        StudyQuery studyQuery = new StudyQuery();
        BeanUtils.copyProperties(objectQuery, studyQuery);
        PageHelper.startPage(studyQuery.getPageNum(), studyQuery.getPageSize());
        List<StudyDto> studyDtoList = studyMapper.findPageBreakByCondition(studyQuery);
        PageInfo bean = new PageInfo<StudyDto>(studyDtoList);
        // 判断是否查询到数据
        if (CollectionUtils.isEmpty(studyDtoList)) {
            throw new FindNullException("提醒: 该条件下一篇文章都木有~ 请尝试更换搜索条件~");
        }
        if (studyQuery.getStudy_type() == 1) {
            List<ArticleListDto> articleListDtoList = new ArrayList<>();
            for (StudyDto s :
                    studyDtoList) {
                ArticleListDto articleListDto = new ArticleListDto();
                BeanUtils.copyProperties(s, articleListDto);
                log.debug("文章查询结果: " + articleListDto.toString());
                try {
                    articleListDto.setClassify(ClassifyEnum.getArticleEnum(s.getClassify()).getName());
                } catch (Exception e) {
                    log.debug("部分Enum 值为空");
                    e.printStackTrace();
                }
                articleListDtoList.add(articleListDto);
            }
            PageInfo pageInfo = new PageInfo<ArticleListDto>(articleListDtoList);
            BeanUtils.copyProperties(bean, pageInfo);
            pageInfo.setList(articleListDtoList);
            return pageInfo;
        }
        List<VideoListDto> videoListDtoList = new ArrayList<>();
        for (StudyDto s :
                studyDtoList) {
            VideoListDto videoListDto = new VideoListDto();
            BeanUtils.copyProperties(s, videoListDto);
            log.debug("视频查询结果: " + videoListDto.toString());
            try {
                videoListDto.setClassify(ClassifyEnum.getArticleEnum(s.getClassify()).getName());
                videoListDto.setSubject(SubjectEnum.getSubjectEnum(s.getSubject()).getSubject());
                videoListDto.setGrade(GradeEnum.getGradeEnum(s.getGrade()).getGrade());
            } catch (Exception e) {
                log.debug("部分Enum 值为空");
                e.printStackTrace();
            }
            videoListDtoList.add(videoListDto);
        }
        PageInfo pageInfo = new PageInfo<VideoListDto>(videoListDtoList);
        BeanUtils.copyProperties(bean, pageInfo);
        pageInfo.setList(videoListDtoList);
        return pageInfo;
    }

    @Override
    public Boolean updateStatusById(Long id) throws Exception {
        if (!studyMapper.updateStatusById(id)) {
            throw new ResourceIsNullException("提醒: 更新的文章不存在~");
        }
        return true;
    }

    @Override
    public ArticleDto findArticleById(Long id) throws Exception {
        Study study = studyMapper.selectByPrimaryKey(id);
        if (study == null) {
            throw new FindNullException("提醒: 该文章不存在~");
        }
        ArticleDto articleDto = new ArticleDto();
        BeanUtils.copyProperties(study, articleDto);
        articleDto.setAuthor(consumeService.findAuthorById(study.getAuthor()));
        return articleDto;
    }

    @Override
    public VideoDto findVideoById(Long id) throws Exception {
        Study study = studyMapper.selectByPrimaryKey(id);
        if (study == null) {
            throw new FindNullException("提醒: 该文章不存在~");
        }
        VideoDto videoDto = new VideoDto();
        BeanUtils.copyProperties(study, videoDto);
        log.debug("findVideoById: " + videoDto.toString());
        return videoDto;
    }

    @Override
    public Long insertVideo(VideoDto videoDto) throws Exception {
        /* 上传图片到OSS */
        if (!"".equals(videoDto.getCover_plan_url()) && videoDto.getCover_plan_url() != null) {
            videoDto.setCover_plan_url(ossService.updateFile(videoDto.getCover_plan_url()));
        }
        Study study = new Study();
        BeanUtils.copyProperties(videoDto, study);
        study.setStudy_type(2);
        study.setCreate_at(new Date());
        study.setUpdate_at(new Date());
        study.setCreate_by(manageService.getOnlineAccount().getUsername());
        study.setUpdate_by(manageService.getOnlineAccount().getUsername());
        log.debug("studyMapper.insert(study) 视频文章新增: " + study.toString());
        studyMapper.insert(study);
        log.debug(study.toString());
        return study.getId();
    }

    // 新增文章
    @Override
    public Long insertArticle(ArticleDto articleDto) throws Exception {
        /* 上传图片到OSS */
        if (!"".equals(articleDto.getCover_plan_url()) && articleDto.getCover_plan_url() != null) {
            articleDto.setCover_plan_url(ossService.updateFile(articleDto.getCover_plan_url()));
            log.debug("articleDto.getCover_plan_url():" + articleDto.getCover_plan_url());
        }
        Study study = new Study();
        BeanUtils.copyProperties(articleDto, study);
        study.setAuthor(consumeService.findAuthorByName(articleDto.getAuthor()));
        study.setStudy_type(1);
        study.setCreate_at(new Date());
        study.setUpdate_at(new Date());
        study.setCreate_by(manageService.getOnlineAccount().getUsername());
        study.setUpdate_by(manageService.getOnlineAccount().getUsername());
        log.debug("studyMapper.insert(study): " + study.toString());
        studyMapper.insert(study);
        log.debug(study.toString());
        return study.getId();
    }

    // 更新文章
    @Override
    public Boolean updateByArticle(ArticleDto articleDto) throws Exception {
        log.info("articleDto.toString(): " + articleDto.toString());
        Study study = new Study();
        BeanUtils.copyProperties(articleDto, study);
        /* 上传图片到OSS */
        if (!"".equals(articleDto.getCover_plan_url()) && articleDto.getCover_plan_url() != null) {
            study.setCover_plan_url(ossService.updateFile(articleDto.getCover_plan_url()));
        }
        study.setStudy_type(1);
        study.setAuthor(consumeService.findAuthorByName(articleDto.getAuthor()));
        study.setUpdate_at(new Date());
        study.setUpdate_by(manageService.getOnlineAccount().getUsername());
        return studyMapper.updateByPrimaryKeySelective(study) > 0;
    }
}