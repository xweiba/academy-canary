package com.ptteng.academy.controller;

import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.ArticleDto;
import com.ptteng.academy.business.dto.StudyDto;
import com.ptteng.academy.business.dto.VideoDto;
import com.ptteng.academy.business.query.ArticleQuery;
import com.ptteng.academy.business.query.VideoQuery;
import com.ptteng.academy.business.vo.ResponseRowsVO;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.service.StudyService;
import com.ptteng.academy.util.FileSaveUtil;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @program: canary
 * @description: 学习充电站相关
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 03:33
 **/

@Slf4j
@Api(tags = "StudyController", description = "文章/视频相关Api")
@RestController
public class StudyController {

    @Resource
    private StudyService studyService;
    @Resource
    private FileSaveUtil fileSaveUtil;


    // 获取文章列表
    @RequiresPermissions(".articles")
    @ApiOperation(value = "文章分页条件查询", notes = "返回文章分页数据")
    @PostMapping("/articles")
    public Object Articles(@RequestBody ArticleQuery articleQuery) throws Exception {
        log.info("articleQuery.toString(): " + articleQuery.toString());
        return ResultUtil.success("获取文章信息成功", studyService.findPageBreakByCondition(articleQuery));
    }

    @RequiresPermissions(".articles")
    @ApiOperation(value = "上下架文章", notes = "返回执行结果")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @PutMapping("/article/{id}/status")
    public ResponseVO articleStatus(@PathVariable("id") Long id) throws Exception {
        return ResultUtil.success("上/下架文章成功", studyService.updateStatusById(id));
    }

    @RequiresPermissions(".articles")
    @ApiOperation(value = "创建文章", notes = "返回article信息")
    @PostMapping("/article")
    public ResponseVO createArticle(@RequestBody ArticleDto articleDto) throws Exception {
        log.debug("创建Article: " + articleDto.toString());
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("id", studyService.insertArticle(articleDto));
        return ResultUtil.success("文章创建成功", objectMap);
    }

    @RequiresPermissions(".articles")
    @ApiOperation(value = "更新文章", notes = "返回article信息")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @PutMapping("/article/{id}")
    public ResponseVO updateArticle(@PathVariable("id") Long id, @RequestBody ArticleDto articleDto) throws Exception {
        articleDto.setId(id);
        return ResultUtil.success("更新成功", studyService.updateByArticle(articleDto));
    }

    @RequiresPermissions(".articles")
    @ApiOperation(value = "获取指定文章", notes = "返回文章信息")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @GetMapping("/article/{id}")
    public ResponseVO getArticle(@PathVariable("id") Long id) throws Exception {
        ArticleQuery articleQuery = new ArticleQuery();
        articleQuery.setId(id);
        return ResultUtil.success("获取文章成功", studyService.findArticleById(id));
    }

    @RequiresPermissions(".videos")
    @ApiOperation(value = "视频文章分页条件查询", notes = "返回文章集合")
    @PostMapping("/videos")
    public Object videos(@RequestBody VideoQuery videoQuery) throws Exception {
        return ResultUtil.success("获取视频信息成功", studyService.findPageBreakByCondition(videoQuery));
    }

    @RequiresPermissions(".videos")
    @ApiOperation(value = "创建视频文章", notes = "返回文章id")
    @PostMapping("/video")
    public ResponseVO createVideo(@RequestBody VideoDto videoDto) throws Exception {
        log.debug("createVideo:" + videoDto.toString());
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("id", studyService.insertVideo(videoDto));
        return ResultUtil.success("视频文章创建成功", objectMap);
    }

    @RequiresPermissions(".videos")
    @ApiOperation(value = "获取视频信息", notes = "获取视频信息")
    @ApiImplicitParam(name = "id", value = "视频Id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @GetMapping("/video/{id}")
    public ResponseVO getVideo(@PathVariable("id") Long id) throws Exception {
        return ResultUtil.success("获取视频成功", studyService.findVideoById(id));
    }

    @RequiresPermissions(".videos")
    @ApiOperation(value = "视频更新", notes = "执行成功返回true")
    @ApiImplicitParam(name = "id", value = "视频Id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @PutMapping("/video/{id}")
    public ResponseVO upDataVideo(@PathVariable("id") Long id, @RequestBody VideoDto videoDto) throws Exception {
        StudyDto studyDto = new StudyDto();
        videoDto.setId(id);
        BeanUtils.copyProperties(videoDto, studyDto);
        return ResultUtil.success("视频更新成功", studyService.updateByPrimaryKeySelective(studyDto));
    }

    @RequiresPermissions(".videos")
    @ApiOperation(value = "上/下架视频", notes = "执行成功返回true")
    @ApiImplicitParam(name = "id", value = "视频Id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @PutMapping("/video/{id}/status")
    public ResponseVO videoStatus(@PathVariable("id") Long id) throws Exception {
        return ResultUtil.success("上/下架视频成功", studyService.updateStatusById(id));
    }

    @RequiresPermissions(value = {".videos", ".articles"}, logical = Logical.OR)
    @ApiOperation(value = "文件上传", notes = "返回处理完毕后的文件名称, 在缓存中保留半小时")
    @PostMapping(value = "/update/image")
    public ResponseVO updateFile(@RequestPart(required = true) MultipartFile update_img) throws Exception {
        if (update_img.isEmpty()) {
            return ResultUtil.error("文件为空");
        }
        // 保存到服务器
        String file_name = fileSaveUtil.saveFile(update_img);
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("image_url", file_name);
        return ResultUtil.success("文件上传成功, 请在半小时内提交数据", objectMap);
    }
}
