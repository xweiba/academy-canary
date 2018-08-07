package com.ptteng.academy.controller.shiro;

import com.alibaba.fastjson.JSONObject;
import com.ptteng.academy.business.dto.AccountDto;
import com.ptteng.academy.business.enums.ResponseCodeEnum;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.business.dto.LoginDto;
import com.ptteng.academy.service.ManageService;
import com.ptteng.academy.util.PasswordUtil;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: canary
 * @description: 登陆验证
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 01:16
 **/


@Slf4j
@Api(tags = "PassportController", description = "登陆相关Api")
@RestController
public class PassportController {

    @Resource
    private ManageService manageService;

    /**
     * @description 登陆验证
     * @param: [loginDto] 记住我可不写
     */
    @ApiOperation(value = "登陆接口", notes = "执行成功返回登陆者信息")
    @PostMapping("/login")
    public ResponseVO Login(@RequestBody LoginDto loginDto) throws Exception {
        log.info("登陆信息:" + loginDto.toString());
        log.debug("加密后的密码: " + PasswordUtil.encrypt(loginDto.getPassWord(), loginDto.getAccountName()));
        // 设置Shiro用户Token测试, true
        UsernamePasswordToken token = new UsernamePasswordToken(loginDto.getAccountName(), loginDto.getPassWord(), loginDto.getRememberMe());
        // UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe); 记住我
        // 获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        // 获取当前的Subject
        // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
        // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
        // 所以这一步在调用login(token)方法时,它会走到xxRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
        currentUser.login(token);
        AccountDto accountDto = manageService.findAccountLoginById(loginDto.getAccountName());
        log.debug("currentUser.getSession(): " + currentUser.getSession().getTimeout() + "-" + currentUser.getSession().getHost() + JSONObject.toJSONString(currentUser.getSession()));
        return ResultUtil.success("登陆成功", accountDto);
    }

    @ApiOperation(value = "退出登陆", notes = "执行成功直接返回无权限")
    @GetMapping("/logout")
    public ResponseVO logout() {
        // http://www.oschina.net/question/99751_91561
        // 此处有坑： 退出登录，其实不用实现任何东西，只需要保留这个接口即可，也不可能通过下方的代码进行退出
        // SecurityUtils.getSubject().logout();
        // 因为退出操作是由Shiro控制的, 但是shiro会直接跳转到未授权页面
        // 获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return ResultUtil.success("您已安全退出");
    }

    /**
     * @description 更新密码
     * @param: [accountDto]
     */
    @ApiOperation(value = "密码更新", notes = "执行成功直接返回提示信息")
    @RequiresPermissions(".password")
    @PutMapping("/account/password")
    public ResponseVO updatePasswor(@RequestBody AccountDto accountDto) throws Exception {
        log.info("updatePasswor: " + accountDto);
        if (manageService.updatePassWord(accountDto)) {
            return ResultUtil.success("密码修改成功");
        }
        return ResultUtil.error("密码错误, 修改失败");
    }
}