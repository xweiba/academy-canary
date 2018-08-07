package com.ptteng.academy.controller;

import com.ptteng.academy.business.dto.StudentCardDto;
import com.ptteng.academy.business.dto.StudentCollectDto;
import com.ptteng.academy.business.enums.GradeEnum;
import com.ptteng.academy.business.enums.ResponseCodeEnum;
import com.ptteng.academy.business.query.StudentCardQuery;
import com.ptteng.academy.business.vo.*;
import com.ptteng.academy.service.SiginService;
import com.ptteng.academy.service.StudentCardService;
import com.ptteng.academy.util.ResultUtil;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:Lin
 * Date:2018/7/18
 * Time:16:47
 */


@RestController
@RequestMapping("/student")
@Api(tags = "StudentController", description = "学生证相关API")
public class StudentController {

    @Autowired
    SiginService siginService;
    @Autowired
    StudentCardService studentCardService;
    @ApiOperation(value = "首页点击签到", notes = "首页查看用户签到信息")
    @ApiImplicitParam(name = "id", value = "用户id",paramType = "path",required = true, dataType = "long")
    @GetMapping("/signing/{id}")
    public ResponseVO getSigning(@PathVariable("id") Long id) throws ParseException {
        try {
            return ResultUtil.success("getSigning 执行了", siginService.selectSiginById(id));
        } catch (NullPointerException e) {
            return ResultUtil.error("指定用户不存在");
        }
    }

    @ApiOperation(value = "签到页中用户点击签到", notes = "通过用户ID判断是否签到")
    @ApiImplicitParam(name = "id", value = "用户id", required = true,paramType = "path", dataType = "long")
    @PutMapping("/signing/{id}")
    public ResponseVO signing(@PathVariable("id") Long id) {
        try {
            return siginService.sigin(id);
        }catch (NullPointerException e) {
            return ResultUtil.error("指定用户不存在");
        }
    }

    @ApiOperation(value = "学生证首页", notes = "通过用户ID获取首页信息")
    @ApiImplicitParam(name = "id", value = "用户id", required = true,paramType = "path", dataType = "long")
    @GetMapping("/card/{id}")
    public ResponseVO studentCard(@PathVariable("id") Long id) {
        try {
            StudentCardDto studentCardDto = studentCardService.selectAll(id);
            return ResultUtil.success("查询成功", studentCardDto);
        } catch (Exception e) {
            return ResultUtil.error("用户不存在");
        }
    }

    @ApiOperation(value = "学生证资料编辑", notes = "需要传用户头像")
    @PutMapping("/card/{id}")
    public ResponseVO updateStudentCard(@PathVariable("id") Long id,@RequestBody @ApiParam(name = "用户对象",value = "传入json格式",required = true) StudentCardQuery studentCardQuery) {
        studentCardQuery.setId(id);
        // 文件和参数一起上传, 待调试
        boolean m = false;
        try {
             m = studentCardService.updateStudentCard(studentCardQuery);
            if (m) {
                return ResultUtil.success("修改成功");
            }
            return ResultUtil.success("修改失败");
        } catch (NullPointerException e) {
            return ResultUtil.error(ResponseCodeEnum.USER_UNEXIST);
        }

    }
    @ApiOperation(value = "获取收藏文章信息", notes = "传入用户ID返回收藏文章列表")
    @ApiImplicitParams({@ApiImplicitParam( name = "id", value = "用户id", paramType = "path",required = true, dataType = "Long"),@ApiImplicitParam(name = "pageNum",value = "显示的页数",required = true,dataType = "int")})
    @PostMapping("/card/collect/articles/{id}")
    public ResponseRowsVO getCollectArticle(@PathVariable("id") Long id, @RequestBody Map<String,Integer> pageNum) {
        try {
            return ResultUtil.success("获取成功", studentCardService.findCollectArticle(id, pageNum.get("pageNum")));
        }catch (NullPointerException e) {
            return ResultUtil.success("用户不存在",studentCardService.findCollectArticle(id, pageNum.get("pageNum")));
        }
    }

    @ApiOperation(value = "获取收藏视频信息", notes = "传入用户ID返回收藏视频列表")
    @ApiImplicitParams({@ApiImplicitParam( name = "id", value = "用户id", paramType = "path",required = true, dataType = "Long"),@ApiImplicitParam(name = "pageNum",value = "显示的页数",required = true,dataType = "int")})
    @PostMapping("/card/collect/videos/{id}")
    public ResponseRowsVO getCollectVideo(@PathVariable("id") Long id, @RequestBody Map<String,Integer> pageNum) {
        return ResultUtil.success("getCollectArticle 已执行", studentCardService.findCollectVideo(id, pageNum.get("pageNum")));
    }
}
