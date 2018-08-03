package com.ptteng.academy.config;

import com.ptteng.academy.shiro.credentials.RetryLimitCredentialsMatcher;
import com.ptteng.academy.shiro.realm.MyShiroRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @program: canary
 * @description: Shiro配置
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-03 01:45
 **/

@Slf4j
@Order(-1)
@Configuration
public class ShiroConfig {

    // Shiro生命周期处理器 必须注入该对象, 下面会调用, 否则 @RequiresPermissions 注解无效
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * @description 配置Shiro过滤器, Shiro启动的核心
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfig>shiroFilter() 执行了");

        // 实例化过滤器
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 实例化权限链表
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        /* 开始权限判断, 由上至下 注意顺序  未登录状态下, 登录后就都可以访问了 */
        // filterChainDefinitionMap.put("/static/**", "anon"); // anon-无需权限即可访问 配置不会拦截的链接
        filterChainDefinitionMap.put("/logout", "logout"); // 配置退出的链接, 只要调用/logout 就会自动退出
        filterChainDefinitionMap.put("/login", "anon"); // 登陆验证调用url, 会自动调用MyShiroRealm.doGetAuthenticationInfo() 去做登陆验证.
        filterChainDefinitionMap.put("/**", "authc"); // 认证过滤, 给所有url加权限, authc-所有url都必须认证通过才可以访问, 这里是认证过滤链
        /* 登陆状态下的过滤 */
        shiroFilterFactoryBean.setUnauthorizedUrl("/403"); // 注意由于("/**", "authc") 过滤的是认证过滤链, 而不是授权过滤链. 所以无法捕获到未授权的. 只能拦截认证过滤链. 这是使用 SimpleMappingExceptionResolver 异常处理去处理未授权的

        /* 以下的都需要登陆状态才能访问, 直接访问都会跳转到/hello */
        shiroFilterFactoryBean.setLoginUrl("/noAccessMsg"); // 无权限跳转
        shiroFilterFactoryBean.setSuccessUrl("/AccessMsg"); // 登陆成功跳转的url

        // 写入权限链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * @description 密码凭证匹配器, 由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了, 这里是做密码加密的配置的
     */
    @Bean(name = "credentialsMatcher")
    public RetryLimitCredentialsMatcher credentialsMatcher() {
        return new RetryLimitCredentialsMatcher();
    }

    /**
     * @description 设置安全数据访问 Realm
     */
    @Bean(name = "myShiroRealm")
    public MyShiroRealm myShiroRealm(@Qualifier("credentialsMatcher") RetryLimitCredentialsMatcher matcher) {
        System.out.println("myShiroRealm() 执行了");
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        // 设置凭证匹配器
        myShiroRealm.setCredentialsMatcher(credentialsMatcher());
        return myShiroRealm;
    }

    /**
     * @description 安全管理器 注意是 org.apache.shiro.mgt.SecurityManager;
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("myShiroRealm") MyShiroRealm myShiroRealm) {
        System.out.println("securityManager() 执行了");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm);

        /*securityManager.setCacheManager(redisCacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 注入记住我管理器
        securityManager.setRememberMeManager(rememberMeManager());*/
        return securityManager;
    }

    /**
     * @description 因为 securityManager 是代理方式执行的, 所以需要开启Shirro AOP支持
     * @param: [securityManager]
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        log.debug("authorizationAttributeSourceAdvisor() 执行了");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /**
     * @description 开启shiro注解, 不开启@RequiresPermissions注解无效
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * @description 异常处理
     */
    @Bean(name = "simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver creatSimpleMappingExceptionResolver() {
        log.debug("creatSimpleMappingExceptionResolver() 执行了");
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "/databaseError"); // 数据库异常
        mappings.setProperty("UnauthorizedException", "/noAccessMsg"); // 捕获权限认证链异常
        simpleMappingExceptionResolver.setExceptionMappings(mappings); // 默认配置, 匹配到上面两个异常
        // simpleMappingExceptionResolver.setDefaultErrorView("error"); // 没有匹配到的异常
        simpleMappingExceptionResolver.setExceptionAttribute("ex"); // 程序抛出的异常

        return simpleMappingExceptionResolver;
    }
}
