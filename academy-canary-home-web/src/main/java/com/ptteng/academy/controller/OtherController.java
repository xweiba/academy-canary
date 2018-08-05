package com.ptteng.academy.controller;

import com.ptteng.academy.business.dto.*;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.service.UtilService;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * description:
 * author:Lin
 * Date:2018/7/28
 * Time:18:44
 */

@Slf4j
@RestController
@RequestMapping("/student")
public class OtherController {

    @Resource
    private UtilService utilService;

    @ApiOperation(value = "获取手机验证码", notes = "传入用户ID和用户电话号码发送验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "phoneId", value = "手机号码", required = true, dataType = "Long")}
    )
    @PutMapping("/card/binding/phone/{id}")
    public ResponseVO StudentBindingPhone(@PathVariable("id") Long id, @RequestBody Map<String, Object> phoneId) {
        String phone = String.valueOf(phoneId.get("phoneId"));
        log.debug("phone:" + phone);
        return ResultUtil.success("StudentBindingPhone 执行了", utilService.sendSMS(id, phone));
    }

    @ApiOperation(value = "效验验证码", notes = "传入验证码效验")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", paramType = "path", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "captcha", value = "验证码", required = true, dataType = "Integer")}
    )
    @PostMapping("/card/binding/phone/{id}")
    public ResponseVO StudentVerifyPhone(@PathVariable("id") Long id, @RequestBody Map<String, Object> captcha) {
        System.out.println(captcha);
        StudentBindStatusDto studentBindStatusVo = new StudentBindStatusDto();
        studentBindStatusVo.setGainBean(20L);
        studentBindStatusVo.setState(true);
        return ResultUtil.success("StudentVerifyPhone 执行了", studentBindStatusVo);
    }

    @ApiOperation(value = "获取邮箱验证码", notes = "传入用户ID和用户邮箱发送验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String")}
    )
    @PostMapping("/card/binding/email/{id}")
    public ResponseVO StudentBindingEmail(@PathVariable("id") Long id, @RequestBody Map<String, Object> email) {
        return ResultUtil.success("StudentBindingEmail 执行了");
    }

    @ApiOperation(value = "效验验证码", notes = "传入验证码效验")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", paramType = "path", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "captcha", value = "验证码", required = true, dataType = "Integer")}
    )
    @PutMapping("/card/binding/email/{id}")
    public ResponseVO StudentVerifyEmail(@PathVariable("id") Long id, @RequestBody Map<String, Object> captcha) {
        StudentBindStatusDto studentBindStatusVo = new StudentBindStatusDto();
        studentBindStatusVo.setGainBean(20L);
        studentBindStatusVo.setState(true);
        return ResultUtil.success("StudentVerifyMail 执行了", studentBindStatusVo);
    }
}





