package com.crowd.mvc.config;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;


/*
* springIoc配置类
* */
@Configuration
@MapperScan("mybatis.mapper")
@ComponentScan({"com.crowd.mvc.config","com.crowd.service"})
@EnableTransactionManagement//开启事务管理
public class SpringConfig {

    //配置一个事务管理器
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Param("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

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

        //创建一个SqlSessionFactoryBean实例
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);//设置数据源

        //指定类路径下的所有类取别名，代替namespace
        sqlSessionFactoryBean.setTypeAliasesPackage(propertiesConfig.getMybatisTypeAliasPackage());
        return sqlSessionFactoryBean;
    }


    //配置mapper映射信息
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(){
        /*
        * MapperScannerConfigurer是为了解决MapperFactoryBean繁琐而生的，有了MapperScannerConfigurer就不需要我们去为每个映射接口去声明一个bean了。大大缩减了开发的效率
        * */
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.crowd.dao");//设置mapper映射文件路径
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSession");//设置sqlSession
        return mapperScannerConfigurer;
    }
}