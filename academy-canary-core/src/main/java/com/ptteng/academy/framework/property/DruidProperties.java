package com.ptteng.academy.framework.property;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * @program: canary
 * @description:
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-25 20:15
 **/

@Configuration
@ConfigurationProperties(prefix = "canary.druid")
@Data
@EqualsAndHashCode(callSuper = false)
@Order(-1)
public class DruidProperties {
    private String username;
    private String password;
    // 该值不可改变
    private String servletPath = "/druid/*";
    private Boolean resetEnable = false;
    private List<String> allowIps;
    private List<String> denyIps;
}
