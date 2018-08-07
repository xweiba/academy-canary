package com.ptteng.academy.controller;

import com.aliyuncs.exceptions.ClientException;
import com.ptteng.academy.business.dto.*;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.framework.exception.ResourceIsNullException;
import com.ptteng.academy.service.StudentCardService;
import com.ptteng.academy.util.MailUtil;
import com.ptteng.academy.service.UtilService;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@Api(tags = "VarifyController", description = "验证相关API")
public class VarifyController {
    @Resource
    MailUtil mailUtil;
    @Autowired
    StudentCardService studentCardService;


    @Resource
    private UtilService utilService;

    @ApiOperation(value = "获取手机验证码", notes = "传入用户ID和用户电话号码发送验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "phoneId", value = "手机号码", required = true, dataType = "Long")}
    )
    @PutMapping("/card/binding/phone/{id}")
    public ResponseVO StudentBindingPhone(@PathVariable("id") Long id, @RequestBody Map<String, Object> phoneId) throws ClientException, ResourceIsNullException {
        log.debug("phone:" + phoneId.get("phoneId"));
        return ResultUtil.success("短信发送成功", utilService.sendSMS(id, String.valueOf(phoneId.get("phoneId"))));
    }

    @ApiOperation(value = "效验短信验证码", notes = "传入验证码效验")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", paramType = "path", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "captcha", value = "验证码", required = true, dataType = "Integer")}
    )
    @PostMapping("/card/binding/phone/{id}")
    public ResponseVO StudentVerifyPhone(@PathVariable("id") Long id, @RequestBody Map<String, String> captcha) throws ResourceIsNullException {
        return ResultUtil.success("短信验证成功", studentCardService.verifyCodePhone(id, captcha.get("captcha")));
    }

    @ApiOperation(value = "获取邮箱验证码", notes = "传入用户ID和用户邮箱发送验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String")}
    )
    @PostMapping("/card/binding/email/{id}")
    public ResponseVO StudentBindingEmail(@PathVariable("id") Long id, @RequestBody Map<String, String> email) throws ResourceIsNullException {
        mailUtil.sendMail(email.get("email"));
        return ResultUtil.success("邮件已发送, 请注意查收");
    }

    @ApiOperation(value = "效验邮箱验证码", notes = "传入验证码效验")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", paramType = "path", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "captcha", value = "验证码", required = true, dataType = "Integer")}
    )
    @PutMapping("/card/binding/email/{id}")
    public ResponseVO StudentVerifyEmail(@PathVariable("id") Long id, @RequestBody Map<String, String> captcha) throws ResourceIsNullException {
        return ResultUtil.success("验证成功", studentCardService.verifyCode(id, captcha.get("captcha")));
    }
}


