package com.ptteng.academy.controller;

import com.ptteng.academy.business.dto.ArticleAndVideoDto;
import com.ptteng.academy.business.dto.HomeBannerListDto;
import com.ptteng.academy.business.dto.HomeVideoListDto;
import com.ptteng.academy.business.dto.StudentCollectDto;
import com.ptteng.academy.business.query.HomeVideoQuery;
import com.ptteng.academy.business.query.PageQuery;
import com.ptteng.academy.business.vo.ResponseRowsVO;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.service.StudyService;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.TypeException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: canary
 * @description: 资料
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-29 02:55
 **/

@Slf4j
@Api(tags = "StudyController", description = "文章/视频相关Api")
@RestController
@RequestMapping("/student")
public class StudyController {

    @Resource
    private StudyService studyService;

    /*视频*/
    @ApiOperation(value = "获取视频Banner列表", notes = "传入实体类")
    @PostMapping("/study/videos")
    public ResponseRowsVO getVideosBanner(@RequestBody HomeVideoQuery homeVideoQuery) {
        return ResultUtil.success("获取视频Banner数据成功", studyService.findVideoBannerByQuery(homeVideoQuery));
    }

    @ApiOperation(value = "下拉刷新获取新card列表", notes = "传入实体类")
    @ApiImplicitParam(name = "homeVideoQuery", value = "实体类其中有每页显示的条数和要显示的页数，还有年级和学科", required = true, dataType = "HomeVideoQuery")
    @PostMapping("/study/videos/pull")
    public ResponseRowsVO getVideos(@RequestBody HomeVideoQuery homeVideoQuery) {
        return ResultUtil.success("下拉刷新获取数据成功", studyService.findVideosByQuery(homeVideoQuery));
    }

    @ApiOperation(value = "获取视频详情", notes = "传入用户ID和视频Id获取视频详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "stuId", value = "传入学员ID", required = true, dataType = "Long"), @ApiImplicitParam(name = "id", value = "视频Id", paramType = "path", required = true, dataType = "Long")})
    @PostMapping("/study/video/{id}")
    public ResponseVO getVideo(@PathVariable("id") Long id, @RequestBody Map<String,Long> stuId) {
        return ResultUtil.success("getVideo 已执行", studyService.findStudyByQuery(id, stuId.get("stuId"), 2));
    }

    @ApiOperation(value = "视频点赞操作", notes = "传入视频id和用户id-stuId点赞操作")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "视频id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "stuId", value = "用户Id", required = true, dataType = "Long")
    })
    @PutMapping("/study/video/{id}/praise")
    public ResponseVO videoPraise(@PathVariable("id") Long id, @RequestBody Map<String,Long> stuId) {
        return ResultUtil.success("videoPraise 已执行", studyService.updatePraiseCollectStatus(id, stuId.get("stuId"), 1));
    }

    @ApiOperation(value = "视频收藏操作", notes = "传入视频id和用户id-stuId收藏操作")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", paramType = "path", value = "视频id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "stuId", value = "用户Id", required = true, dataType = "Long")
    })
    @PutMapping("/study/video/{id}/collect")
    public ResponseVO videoCollect(@PathVariable("id") Long id,@RequestBody  Map<String,Long> stuId) {
        log.info("videoCollect传入参数: " + id + stuId);
        return ResultUtil.success("videoPraise 已执行", studyService.updatePraiseCollectStatus(id, stuId.get("stuId"), 2));
    }

    @ApiOperation(value = "获取banner文章信息", notes = "要显示的页数")
    @PostMapping("/study/article/banners")
    public ResponseRowsVO getArticleBanner(@ApiParam @RequestBody Map<String,Integer> num) throws Exception {
        Integer integer = num.get("num");
        if (integer == null) {
            throw new NullPointerException();
        }
        List<HomeBannerListDto> banners = studyService.findArticleBanneryByQuery(integer);
        return ResultUtil.success("getArticleBanner 已执行", banners);
    }

    @ApiOperation(value = "获取card文章列表", notes = "传入实体类")
    @PostMapping("/study/articles")
    public ResponseRowsVO getArticles(@ApiParam(name = "pageQuery", value = "实体类其中有每页显示的条数和要显示的页数") @RequestBody PageQuery pageQuery) {
        return ResultUtil.success("getArticles 已执行", studyService.findArticleByQuery(pageQuery));
    }

    @ApiOperation(value = "获取card文章详情信息", notes = "通过文章id获取详情")
    @ApiImplicitParam(name = "id", value = "文章id", required = true,paramType = "path",dataType = "Long")
    @PostMapping("/study/article/{id}")
    public ResponseVO getArticle(@PathVariable("id") Long id, @ApiParam (name = "stuId", value = "用户Id", required = true) @RequestBody Map<String,Long> stuId) {
        return ResultUtil.success("getArticle 已执行", studyService.findArticleById(id,stuId.get("stuId")));
    }

    @ApiOperation(value = "文章点赞操作", notes = "传入文章id和用户id-stuId点赞操作")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "视频id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "stuId", value = "用户Id", required = true, dataType = "Long")
    })
    @PutMapping("/study/article/{id}/praise")
    public ResponseVO articlePraise(@PathVariable("id") Long id,@RequestBody Map<String,Long> stuId) {
        return ResultUtil.success("点赞操作成功", studyService.updatePraiseCollectStatus(id, stuId.get("stuId"), 1));
    }

    @ApiOperation(value = "文章收藏操作", notes = "传入视频id和用户id-stuId收藏操作")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", paramType = "path", value = "视频id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "stuId", value = "用户Id", required = true, dataType = "Long")
    })
    @PutMapping("/study/article/{id}/collect")
    public ResponseVO articleCollect(@PathVariable("id") Long id,@RequestBody Map<String,Long> stuId) {
        log.info("videoCollect传入参数: " + id + stuId);
        return ResultUtil.success("收藏操作成功", studyService.updatePraiseCollectStatus(id,stuId.get("stuId") , 2));
    }
}