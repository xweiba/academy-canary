package com.ptteng.academy.framework.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @program: canary
 * @description: 阿里云短信配置
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-04 21:59
 **/

@Configuration
@ConfigurationProperties(prefix = "canary.alySMS")
@Data
@Order(-1)
public class AliyunSMSProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String product;
    private String domain;
    private String regionId;
    private String signName;
    private String dateFormat;
    private String endpointName;
    private String templateCode;
}
