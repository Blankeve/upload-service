package com.novedu.nov.upload_service.config;

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
 * @author ：juam
 * @date ：2021/12/8 14:25
 * @description：
 * @modified By：
 * @version:
 */
@Configuration
@EnableSwagger2
//是否开启swagger，正式环境一般是需要关闭的（避免不必要的漏洞暴露！），可根据springboot的多环境配置进行设置
//@ConditionalOnProperty(name = "swagger.enable",  havingValue = "true")
public class SwaggerConfig {

    @Bean
    public Docket getUserDocket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("课程中心API文档")//api标题
                .description("课程中心相关接口描述")//api描述
                .version("0.0.7")//版本号
                .contact(new Contact("juam", "", ""))//本API负责人的联系信息
                .build();
        return new Docket(DocumentationType.SWAGGER_2)//文档类型（swagger2）
                .apiInfo(apiInfo)//设置包含在json ResourceListing响应中的api元信息
                .select()//启动用于api选择的构建器
                .apis(RequestHandlerSelectors.basePackage("com.novedu.nov"))//扫描接口的包
                .paths(PathSelectors.any())//路径过滤器（扫描所有路径）
                .build();
    }


}
