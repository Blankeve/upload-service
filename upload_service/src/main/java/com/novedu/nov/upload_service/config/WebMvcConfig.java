package com.novedu.nov.upload_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.util.UrlPathHelper;

/**
 * @author ：juam
 * @date ：2022/1/5 13:50
 * @description：
 * @modified By：
 * @version:
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${user.filePath}")
    private String filePath;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setUrlDecode(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("**/img/**")
                .addResourceLocations("file:"+filePath);
        registry.addResourceHandler("**/video/**")
                .addResourceLocations("file:"+filePath);
    }
}
