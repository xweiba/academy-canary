package com.ptteng.academy.controller.shiro;

import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.business.dto.LoginDto;
import com.ptteng.academy.util.PasswordUtil;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

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

    /**
     * @description 登陆验证
     * @param: [loginDto] 记住我可不写
     */
    @PostMapping("/login")
    public ResponseVO Login(@RequestBody LoginDto loginDto) {
        log.info("登陆信息:" + loginDto.toString());
        System.out.println("HomeController.login()");
        try {
            System.out.println("加密后的密码: " + PasswordUtil.encrypt(loginDto.getPassWord(),loginDto.getAccountName()));
            // 设置Shiro用户Token
            UsernamePasswordToken token = new UsernamePasswordToken(loginDto.getAccountName(), loginDto.getPassWord(), loginDto.getRememberMe());
            // UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe); 记住我

            // 获取当前的Subject
            Subject currentUser = SecurityUtils.getSubject();

            // 登录失败从request中获取shiro处理的异常信息。
            // shiroLoginFailure:就是shiro异常类的全类名.
            //获取当前的Subject
            try {
                // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
                // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
                // 所以这一步在调用login(token)方法时,它会走到xxRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
                currentUser.login(token);
                return ResultUtil.success("登陆成功", loginDto);
            } catch (Exception e) {
                token.clear();
                return ResultUtil.error("登录失败，用户名: "+ loginDto.getAccountName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    @RequestMapping("/noAccessMsg")
    public ResponseVO noAccessMsg() {
        return ResultUtil.error("您无权访问该接口");
    }
    @RequestMapping("/accessMsg")
    public ResponseVO accessMsg() {
        return ResultUtil.success("登陆成功");
    }
}
