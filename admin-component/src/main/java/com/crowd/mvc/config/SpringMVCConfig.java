package com.crowd.mvc.config;

import com.crowd.mvc.interceptor.LoginInterceptor;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


import java.util.List;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan({"com.crowd.mvc.controller","com.crowd.service"})
public class SpringMVCConfig implements WebMvcConfigurer {


    @Bean("internalResourceViewResolver")
    public InternalResourceViewResolver getInternalResourceViewResolver(@Param("propertiesConfig")  PropertiesConfig propertiesConfig){
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix(propertiesConfig.getPrefix());//前缀
        internalResourceViewResolver.setSuffix(propertiesConfig.getSuffix());//后缀
        return internalResourceViewResolver;
    }




        //添加拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).
                addPathPatterns("/**").
                excludePathPatterns("/admin/toLogin.html").
                excludePathPatterns("/admin/logout.html").
                excludePathPatterns("/admin/doLogin.html").
                excludePathPatterns("/");
    }

    //开启默认servlet处理静态文件的访问！
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/toLogin.html").setViewName("admin-login");
        registry.addViewController("/admin/toMain.html").setViewName("admin-main");
        registry.addViewController("/admin/toAdd.html").setViewName("admin-add");
        registry.addViewController("/").setViewName("index");
    }

    // 配置异常
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty("com.crowd.exception.AccessForbiddenException","admin-login");
        simpleMappingExceptionResolver.setExceptionMappings(properties);
        resolvers.add(simpleMappingExceptionResolver);
    }



}
