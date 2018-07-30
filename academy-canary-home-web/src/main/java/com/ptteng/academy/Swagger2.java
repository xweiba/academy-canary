package com.ptteng.academy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: canary
 * @description: Swagger2 API 入口配置
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-24 11:17
 **/

@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ptteng.academy.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("求学大作战api测试")
                .description("前台")
                .termsOfServiceUrl("http://academy.home.canary.dounixue.net")
                .contact(new Contact("canary","http://academy.admin.canary.dounixue.net","test@email"))
                .build();
    }
}
