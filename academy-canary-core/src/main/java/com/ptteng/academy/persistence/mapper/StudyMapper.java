package com.ptteng.academy.persistence.mapper;

import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.*;
import com.ptteng.academy.business.query.HomeVideoQuery;
import com.ptteng.academy.business.query.PageQuery;
import com.ptteng.academy.business.query.StudyQuery;
import com.ptteng.academy.persistence.beans.Study;
import com.ptteng.academy.plugin.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @program: canary
 * @description: 学习
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-26 15:30
 **/
@Repository
public interface StudyMapper extends BaseMapper<Study>{
    // 获取后台文章集合数据
    List<StudyDto> findPageBreakByCondition(StudyQuery studyQuery) throws NullPointerException;
    // 修改文章状态
    Boolean updateStatusById(Long id);
    // 获取前台视频集合数据
    List<HomeVideoListDto> findVideoByVideoQuery(HomeVideoQuery homeVideoQuery);
    // 获取前台banner集合数据
    List<HomeBannerListDto> findBannerByQuery(Object objectQuery);
    // 获取前台视频详细信息
    HomeVideoDto findStudyByQuery(@Param("studyId")Long studyId, @Param("userId")Long userId, @Param("studyType")Integer studyType);
    // 查询点赞/收藏状态
    Boolean findPraiseCollectStatus(@Param("studyId")Long studyId, @Param("userId")Long userId, @Param("type")Integer type);
    // 取消点赞/收藏状态
    Boolean deletePraiseCollectStatus(@Param("studyId")Long studyId, @Param("userId")Long userId, @Param("type")Integer type);
    // 点赞/收藏
    Boolean insertPraiseCollectStatus(@Param("studyId")Long studyId, @Param("userId")Long userId, @Param("type")Integer type, @Param("createAt")Date createAt);

    // 获取前台banner文章集合数据
    List<HomeBannerListDto> findBannerArticleByQuery(@Param("num")Integer num);

    //获取前台card文章集合
    List<HomeArticleListDto> findArticleByQuery();

    //获取用户收藏文章集合
    List<StudentCollectArticleDto> findCollectArticle(@Param("id")Long id);
    //获取用户收藏视频集合
    List<HomeVideoDto> findCollectVideo(@Param("id")Long id);
}
