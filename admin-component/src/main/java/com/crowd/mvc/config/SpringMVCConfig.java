package com.crowd.mvc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.crowd.mvc.interceptor.LoginInterceptor;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;



import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan({"com.crowd.mvc.controller","com.crowd.service"})
public class SpringMVCConfig implements WebMvcConfigurer {

    //配置数据源
    @Bean("dataSource")
    public DataSource getDataSource(@Param("propertiesConfig") PropertiesConfig propertiesConfig){
        DruidDataSource druidDataSource = new DruidDataSource();//采用德鲁伊连接池
        druidDataSource.setUsername(propertiesConfig.getUsername());
        druidDataSource.setPassword(propertiesConfig.getPassword());
        druidDataSource.setUrl(propertiesConfig.getUrl());
        druidDataSource.setDriverClassName(propertiesConfig.getDriver());
        return druidDataSource;
    }

    //添加拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        //未登录会被拦截
        registry.addInterceptor(new LoginInterceptor()).
                addPathPatterns("/**").
                excludePathPatterns("/admin/toLogin").
                excludePathPatterns("/admin/logout").
                excludePathPatterns("/admin/doLogin").
                excludePathPatterns("/").
                excludePathPatterns("/hello");
    }

    //开启默认servlet处理静态文件的访问！
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/toLogin").setViewName("admin-login");
        registry.addViewController("/admin/toMain").setViewName("admin-main");
        registry.addViewController("/admin/toAdd").setViewName("admin-add");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/hello").setViewName("hello");
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
