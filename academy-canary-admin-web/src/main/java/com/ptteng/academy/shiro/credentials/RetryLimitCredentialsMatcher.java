package com.ptteng.academy.shiro.credentials;

import com.ptteng.academy.business.consts.SessionConst;
import com.ptteng.academy.business.dto.AccountDto;
import com.ptteng.academy.persistence.beans.Account;
import com.ptteng.academy.service.ManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: canary
 * @description: Shiro-密码输入错误的状态下重试次数的匹配管理
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-03 01:24
 **/

@Slf4j
public class RetryLimitCredentialsMatcher extends CredentialsMatcher {

    /**
     * 用户登录次数计数  redisKey 前缀
     */
    private static final String SHIRO_LOGIN_COUNT = "shiro_login_count_";
    /**
     * 用户登录是否被锁定    一小时 redisKey 前缀
     */
    private static final String SHIRO_IS_LOCK = "shiro_is_lock_";
    /*    @Autowired
        private RedisTemplate redisTemplate;*/
    @Autowired
    private ManageService manageService;

    // 重写父类方法
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        log.info("RetryLimitCredentialsMatcher -> doCredentialsMatch" + info.getPrincipals().getPrimaryPrincipal());
        // 此时登陆信息还未存储, 不能使用manageService 的方法获取登陆账户信息
        AccountDto accountDto =  (AccountDto) info.getPrincipals().getPrimaryPrincipal();
        log.info("accountDto:" + accountDto.toString());
        Long accountId = accountDto.getId();
        log.info("accountId: " + accountId);
        AccountDto account = manageService.findAccountById(accountId);
        log.info("account:" + account.toString());
        String username = account.getUsername();
       /* // 访问一次，计数一次
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String loginCountKey = SHIRO_LOGIN_COUNT + username;
        String isLockKey = SHIRO_IS_LOCK + username;
        opsForValue.increment(loginCountKey, 1);

        if (redisTemplate.hasKey(isLockKey)) {
            throw new ExcessiveAttemptsException("帐号[" + username + "]已被禁止登录！");
        }

        // 计数大于5时，设置用户被锁定一小时
        String loginCount = String.valueOf(opsForValue.get(loginCountKey));
        int retryCount = (5 - Integer.parseInt(loginCount));
        if (retryCount <= 0) {
            opsForValue.set(isLockKey, "LOCK");
            redisTemplate.expire(isLockKey, 1, TimeUnit.HOURS);
            redisTemplate.expire(loginCountKey, 1, TimeUnit.HOURS);
            throw new ExcessiveAttemptsException("由于密码输入错误次数过多，帐号[" + username + "]已被禁止登录！");
        }*/

        // 调用父类方法做验证
        boolean matches = super.doCredentialsMatch(token, info);

        if (!matches) {
            // String msg = retryCount <= 0 ? "您的账号一小时内禁止登录！" : "您还剩" + retryCount + "次重试的机会";
            // throw new AccountException("帐号或密码不正确！" + msg);
            throw new AccountException("帐号或密码不正确！");
        }

        //清空登录计数
        // redisTemplate.delete(loginCountKey);
        // 当验证都通过后，把用户信息放在session里
        // 注：Account必须实现序列化
        SecurityUtils.getSubject().getSession().setAttribute(SessionConst.ACCOUNT_SESSION_KEY, account);
        return true;
    }
}
