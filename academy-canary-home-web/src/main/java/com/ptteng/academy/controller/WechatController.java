package com.ptteng.academy.controller;

import com.ptteng.academy.business.dto.WeChatTokenDto;
import com.ptteng.academy.business.dto.WeChatUserDto;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.service.WechatService;
import com.ptteng.academy.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 * author:Lin
 * Date:2018/7/28
 * Time:11:14
 */

@RestController
@RequestMapping("/student")
public class WechatController {
    @Autowired
    WechatService wechatService;
    @RequestMapping("/code")
    public ResponseVO mychatAccredit(@RequestBody String code){
        WeChatUserDto weChatUserDto = wechatService.userLogin(code);
        return ResultUtil.success("微信登陆成功",weChatUserDto);
    }
}
