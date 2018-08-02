package com.ptteng.academy.shiro.credentials;

import com.ptteng.academy.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @program: canary
 * @description: Shiro-密码凭证匹配器（验证密码有效性）
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-03 01:15
 **/

@Slf4j
public class CredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        log.info("CredentialsMatcher->doCredentialsMatch");
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;
        //获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
        String inPassword = new String(utoken.getPassword());
        log.info("inPassword:" + inPassword);
        //获得数据库中的密码
        String dbPassword = (String) info.getCredentials();
        try {
            dbPassword = PasswordUtil.decrypt(dbPassword, utoken.getUsername());
            log.info("dbPassword:" + dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        //进行密码的比对
        return this.equals(inPassword, dbPassword);
    }
}