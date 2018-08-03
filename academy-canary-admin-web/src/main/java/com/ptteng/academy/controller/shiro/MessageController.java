package com.ptteng.academy.controller.shiro;

import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: canary
 * @description: 提示跳转页面
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-03 16:19
 **/

@RestController
@Api(tags = "MessageController", description = "提示跳转页面")
public class MessageController {
    @ApiOperation(value = "无权限跳转页面", notes = "访问无权限接口时自动调用")
    @RequestMapping("/noAccessMsg")
    public ResponseVO noAccessMsg() {
        return ResultUtil.error("您无权访问该接口");
    }

    @ApiOperation(value = "登陆成功跳转", notes = "测试无效")
    @RequestMapping("/accessMsg")
    public ResponseVO accessMsg() {
        return ResultUtil.success("登陆成功");
    }
}
