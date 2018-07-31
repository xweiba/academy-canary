package com.ptteng.academy.controller;

import com.ptteng.academy.business.dto.WeChatTokenDto;
import com.ptteng.academy.business.dto.WeChatUserDto;
import com.ptteng.academy.business.dto.WeixinAccessToken;
import com.ptteng.academy.business.dto.WexinJsapiTicket;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.service.WechatService;
import com.ptteng.academy.util.ResultUtil;
import com.ptteng.academy.util.WeChatUtil;
import com.ptteng.academy.util.WechatSignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Map;

/**
 * description:
 * author:Lin
 * Date:2018/7/28
 * Time:11:14
 */

@Api(tags = "WechatController", description = "微信相关Api")
@RestController
@RequestMapping("/a/student")
public class WechatController {
    @Autowired
    WechatService wechatService;

    @ApiOperation(value = "微信登陆", notes = "获取用户code值登陆")
    @GetMapping("/{code}")
    public ResponseVO mychatAccredit(@PathVariable String code) {
        WeChatUserDto weChatUserDto = wechatService.userLogin(code);
        return ResultUtil.success("微信登陆成功", weChatUserDto);
    }

    @ApiOperation(value = "获取微信接口权限", notes = "传入访问接口的url")
    @PostMapping("/authority")
    public ResponseVO muchatAuthority(@RequestBody String url) throws Exception {
        String jsapiTicket = null;
            jsapiTicket = WexinJsapiTicket.getJsapiTicket();
        // 注意 URL 一定要动态获取，不能 hardcode(硬编码), 签名用的url必须是调用JS接口页面的完整URL。
        // String url = args[0];
        Map<String, String> ret = WechatSignUtil.getWexinAuthority(jsapiTicket, url);
        return ResultUtil.success("获取成功：", ret);
    }
}
