package com.ptteng.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: canary
 * @description: SpringBoot 后端WEB启动类
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 00:15
 **/

@SpringBootApplication
public class CanaryAdminLaunch {
    public static void main(String[] args) {
        SpringApplication.run(CanaryAdminLaunch.class, args);
    }
}
