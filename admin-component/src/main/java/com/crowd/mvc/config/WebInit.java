package com.crowd.mvc.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.Filter;

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    /*
    * 注册springIco配置类
    * */
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }
    /*
    * 注册springMvc配置类
    * */
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMVCConfig.class};
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return super.createRootApplicationContext();
    }


    /*
    * 设置dispatchServlet请求映射地址
    * */
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /*
    * 添加过滤器
    * */
    @Override
    protected Filter[] getServletFilters() {
        //请求响应编码
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
//        characterEncodingFilter.setForceResponseEncoding(true);

        //支持restful风格
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return new Filter[]{characterEncodingFilter,hiddenHttpMethodFilter};

    }
}
