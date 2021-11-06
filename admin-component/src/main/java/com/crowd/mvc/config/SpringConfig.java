package com.crowd.mvc.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
/*
* springIoc配置类
* */

@Configuration
@MapperScan("mybatis.mapper")
@ComponentScan({"com.crowd.mvc.config"})
public class SpringConfig {

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

    /*
    * 配置mybatis
    * */
    @Bean("sqlSession")
    public SqlSessionFactoryBean getDataSourceFactory(@Param("dataSource") DataSource dataSource,@Param("propertiesConfig")PropertiesConfig propertiesConfig){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();//创建一个SqlSessionFactoryBean实例
        sqlSessionFactoryBean.setDataSource(dataSource);
        //指定类路径下的所有类取别名，代替namespace
        sqlSessionFactoryBean.setTypeAliasesPackage(propertiesConfig.getMybatisTypeAliasPackage());
        //扫描mapper包路径
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resource = resolver.getResource(propertiesConfig.getMapperLocations());//
        sqlSessionFactoryBean.setMapperLocations(resource);
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

}
