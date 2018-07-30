package com.ptteng.academy.controller;

import com.ptteng.academy.business.vo.*;
import com.ptteng.academy.service.SiginService;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * Created with IDEA
 * author:Lin
 * Date:2018/7/18
 * Time:16:47
 */


@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    SiginService siginService;
    @ApiOperation(value = "首页点击签到", notes = "首页查看用户签到信息")
    @ApiImplicitParam(name = "id", value = "用户id",paramType = "path",required = true, dataType = "long")
    @GetMapping("/signing/{id}")
    public ResponseVO getSigning(@PathVariable("id") Long id) throws ParseException {
        return ResultUtil.success("getSigning 执行了", siginService.selectSiginById(id));
    }

    @ApiOperation(value = "签到页中用户点击签到", notes = "通过用户ID判断是否签到")
    @ApiImplicitParam(name = "id", value = "用户id", required = true,paramType = "path", dataType = "long")
    @PutMapping("/signing/{id}")
    public ResponseVO signing(@PathVariable("id") Long id) {
        return siginService.sigin(id);
    }
}
