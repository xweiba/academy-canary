package com.ptteng.academy.shiro.realm;

import com.ptteng.academy.business.dto.AccountDto;
import com.ptteng.academy.business.dto.ModuleDto;
import com.ptteng.academy.business.dto.RoleDto;
import com.ptteng.academy.persistence.beans.Account;
import com.ptteng.academy.service.ManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.StringUtils;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: canary
 * @description: 安全数据域
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-02 16:19
 **/

@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    ManageService manageService;

    /**
     * @description 主要用作权限验证, 在shiroFilterFactoryBean.setLoginUrl("/hello")被Filter过滤器拦截到时调用.
     * @param: [authenticationToken]
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("密码效验-->MyShiroRealm.doGetAuthenticationInfo()");
        // 获取用户的输入的账号.
        String username = (String) authenticationToken.getPrincipal();
        authenticationToken.getPrincipal();
        System.out.println("获取登陆用户名:" + username);
        System.out.println("authenticationToken.getCredentials(): " + authenticationToken.getCredentials());
        // 通过username从数据库中查找账号对象，如果找到，没找到.
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        AccountDto accountDto = manageService.findAccountByUsername(username);
        System.out.println("----->>account=" + accountDto.toString());

        // 如果不存在
        if (accountDto == null) {
            // 直接返回空值
            return null;
        }

        // 如果存在, 注意 SimpleAuthorizationInfo 是存入链接权限的, SimpleAuthenticationInfo是验证权限的
        // 由SimpleAuthenticationInfo进行权限验证
        System.out.println("MyShiroRealm.doGetAuthenticationInfo().SimpleAuthenticationInfo");
        System.out.println("传入参数: account:" + accountDto.toString() + "getName():" + getName());
        System.out.println("密码加盐- ByteSource.Util.bytes(account.getUsername()) : " + ByteSource.Util.bytes(username));
        SimpleAuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(
                accountDto, // 存入账号信息
                accountDto.getPassword(), // 存入数据库中密码
                ByteSource.Util.bytes(username), // 加盐操作, 这里使用用户名作为盐值
                getName() // realm 的名字: ml.xiaoweiba.config.MyShiroRealm_0
        );
        return authorizationInfo;
    }


    /**
     * @description 链接权限的实现 主要做权限赋值shiro的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo()来更新. 当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行，所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可。在这个方法中主要是使用类：SimpleAuthorizationInfo进行角色的添加和权限的添加。
     * @param: [principalCollection] PrincipalCollection是一个身份集合，因为我们现在就一个Realm，所以直接调用getPrimaryPrincipal得到之前传入的用户名即可；然后根据用户名调用AccountService接口获取角色及权限信息。
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        // SimpleAuthorizationInfo 用户存放认证的角色和权限信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        // 将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
        // 这里可以从数据库中查询来达到修改立即生效
        AccountDto accountDto = (AccountDto) principalCollection.getPrimaryPrincipal(); // 获取认证时存入authorizationInfo的Account信息
        log.debug("accountDto.getRole_id() " + accountDto.getRole_id());
        // 获取角色信息
        RoleDto roleDto = manageService.findRoleById(accountDto.getRole_id());
        log.debug("roleDto.toString():" + roleDto.toString());
        // 赋予角色
        authorizationInfo.addRole(roleDto.getRole_tag());

        // 获取模块信息
        for (Long moduleId :
                roleDto.getModuleIds()) {

            String moduleDto = manageService.findModuleById(moduleId).getModule_url();
            // 注意 addStringPermission 不能添加为空得权限
            if (!StringUtil.isEmpty(moduleDto)) {
                log.debug("Module_url: " + moduleDto);
                // 添加所属权限
                authorizationInfo.addStringPermission(moduleDto);
            }
        }
        log.debug("authorizationInfo.toString():" + authorizationInfo.getStringPermissions());
        log.debug("authorizationInfo.getRoles():" + authorizationInfo.getRoles());
        return authorizationInfo;
    }
}