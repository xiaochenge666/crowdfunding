package com.crowd.mvc.config;

import com.crowd.mvc.interceptor.LoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan({"com.crowd.mvc.controller"})
public class SpringMVCConfig implements WebMvcConfigurer, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private Logger logger= LoggerFactory.getLogger(this.getClass());

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

    //直接映射到指定的视图页面（相当于controller只起转发作用，没有实际的业务逻辑）
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



    //配置视图解析器
    @Bean
    public ViewResolver viewResolver() {

        /*
         * 配置视图解析器
         * */
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");

        //配置模板引擎
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();

        //配置模板解析器
        SpringResourceTemplateResolver templateResolver =new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        //将模板解析器设置给模板引擎
        templateEngine.setTemplateResolver(templateResolver);
        //将模板引擎设置给视图解析器
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("设置了ApplicationContext!");
        this.applicationContext = applicationContext;
    }
}
