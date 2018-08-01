package com.ptteng.academy.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @program: canary
 * @description: Mybaits 扫描配置
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-26 01:44
 **/

@EnableTransactionManagement
@Component
@MapperScan("com.ptteng.academy.persistence.mapper")
public class MybatisConfig {
}
