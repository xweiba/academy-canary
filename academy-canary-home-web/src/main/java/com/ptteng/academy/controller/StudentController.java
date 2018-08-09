package com.ptteng.academy.controller;

import com.ptteng.academy.business.query.StudentCardQuery;
import com.ptteng.academy.business.vo.*;
import com.ptteng.academy.framework.exception.FindNullException;
import com.ptteng.academy.framework.exception.ResourceIsNullException;
import com.ptteng.academy.service.SiginService;
import com.ptteng.academy.service.StudentCardService;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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
@Validated
public class StudentController {

    @Autowired
    SiginService siginService;
    @Autowired
    StudentCardService studentCardService;

    @ApiOperation(value = "首页点击签到", notes = "首页查看用户签到信息")
    @ApiImplicitParam(name = "id", value = "用户id", paramType = "path", required = true, dataType = "long")
    @GetMapping("/signing/{id}")
    public ResponseVO getSigning(@Min(value = 0,message = "参数不能小于0")@PathVariable("id") Long id) throws Exception {
        return ResultUtil.success("查看用户签到信息成功", siginService.selectSiginById(id));
    }
    @ApiOperation(value = "签到页中用户点击签到", notes = "通过用户ID判断是否签到")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "long")
    @PutMapping("/signing/{id}")
    public ResponseVO signing(@Min(value = 0,message = "参数不能小于0")@PathVariable("id") Long id) throws ResourceIsNullException {
        return ResultUtil.success("签到成功", siginService.sigin(id));
    }

    @ApiOperation(value = "学生证首页", notes = "通过用户ID获取首页信息")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "long")
    @GetMapping("/card/{id}")
    public ResponseVO studentCard(@Min(value = 0,message = "参数不能小于0")@PathVariable("id") Long id) throws Exception{
        return ResultUtil.success("学生证首页查询成功", studentCardService.selectAll(id));
    }

    @ApiOperation(value = "学生证资料编辑", notes = "需要传用户头像")
    @PutMapping("/card/{id}")
    public ResponseVO updateStudentCard(@Min(value = 0,message = "参数不能小于0")@PathVariable("id") Long id,@RequestBody @ApiParam(name = "用户对象",value = "传入json格式",required = true) StudentCardQuery studentCardQuery) throws ResourceIsNullException {
        studentCardQuery.setId(id);
        // 文件和参数一起上传, 待调试
        studentCardService.updateStudentCard(studentCardQuery);
        return ResultUtil.success("学生证资料修改成功");
    }

    @ApiOperation(value = "获取收藏文章信息", notes = "传入用户ID返回收藏文章列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", paramType = "path", required = true, dataType = "Long"), @ApiImplicitParam(name = "pageNum", value = "显示的页数", required = true, dataType = "int")})
    @PostMapping("/card/collect/articles/{id}")
    public ResponseRowsVO getCollectArticle(@Min(value = 0,message = "参数不能小于0")@PathVariable("id") Long id, @Min(value = 0,message = "参数不能小于0")@RequestBody Map<String,Integer> pageNum) throws FindNullException {
        return ResultUtil.success("获取收藏文章信息成功", studentCardService.findCollectArticle(id, pageNum.get("pageNum")));
    }

    @ApiOperation(value = "获取收藏视频信息", notes = "传入用户ID返回收藏视频列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", paramType = "path", required = true, dataType = "Long"), @ApiImplicitParam(name = "pageNum", value = "显示的页数", required = true, dataType = "int")})
    @PostMapping("/card/collect/videos/{id}")
    public ResponseRowsVO getCollectVideo(@Min(value = 0,message = "参数不能小于0")@PathVariable("id") Long id, @Min(value = 0,message = "参数不能小于0")@RequestBody Map<String,Integer> pageNum) throws FindNullException {
        return ResultUtil.success("获取收藏视频信息成功", studentCardService.findCollectVideo(id, pageNum.get("pageNum")));
    }
}
