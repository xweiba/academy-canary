package com.ptteng.academy.framework.property;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @program: canary
 * @description: 七牛云配置
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-27 22:13
 **/

@Configuration
@ConfigurationProperties(prefix = "canary.qiniu")
@Data
@Order(-1)
public class QiNiuYunProperties {
    private String access_key_id;
    private String access_key_secret;
    private String bucket_name;
    private String endpoint;
    private String file_url;
}
