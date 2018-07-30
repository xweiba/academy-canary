package com.ptteng.academy.controller.shiro;

import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.business.dto.LoginDto;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @program: canary
 * @description: 登陆验证
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 01:16
 **/

@Api(tags = "PassportController", description = "登陆相关Api")
@RestController
public class PassportController {

    /**
     * @description 登陆验证
     * @param: [loginDto] 记住我可不写
     */
    @PostMapping("/login")
    public ResponseVO Login(@RequestBody LoginDto loginDto) {
        loginDto.setRoleName("管理员");
        loginDto.setAccountName("admin");
        loginDto.setId(1L);
        return ResultUtil.success("登陆成功", loginDto);
    }

    @GetMapping("/logout")
    public ResponseVO logout() {
        // http://www.oschina.net/question/99751_91561
        // 此处有坑： 退出登录，其实不用实现任何东西，只需要保留这个接口即可，也不可能通过下方的代码进行退出
        // SecurityUtils.getSubject().logout();
        // 因为退出操作是由Shiro控制的
        return ResultUtil.success("您已安全退出");
    }
}
