package com.crowd.mvc.config;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


import javax.servlet.ServletContext;
import javax.sql.DataSource;
/*
* springIoc配置类
* */

@Configuration
@MapperScan("mybatis.mapper")
@ComponentScan({"com.crowd.mvc.config"})
public class SpringConfig implements ApplicationContextAware {

    private WebApplicationContext applicationContext;

    @Bean
    public ViewResolver viewResolver() {
        /*
         * Thymeleaf模板引擎
         * */
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();



        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        // ServletContextTemplateResolver需要一个ServletContext作为构造参数，可通过WebApplicationContext 的方法获得
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(this.applicationContext.getServletContext());

        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        templateEngine.setTemplateResolver(templateResolver);
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }



    /*
    * 配置mybatis
    * */
    @Bean("sqlSession")
    public SqlSessionFactoryBean getDataSourceFactory(@Param("dataSource") DataSource dataSource,@Param("propertiesConfig")PropertiesConfig propertiesConfig){

        //创建一个SqlSessionFactoryBean实例
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //指定类路径下的所有类取别名，代替namespace
        sqlSessionFactoryBean.setTypeAliasesPackage(propertiesConfig.getMybatisTypeAliasPackage());
        //扫描mapper包路径
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource resource = resolver.getResource(propertiesConfig.getMapperLocations());//
//        sqlSessionFactoryBean.setMapperLocations(resource);

        return sqlSessionFactoryBean;
    }

    //配置mapper
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.crowd.dao");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSession");
        return mapperScannerConfigurer;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext= (WebApplicationContext) applicationContext;
    }
}
