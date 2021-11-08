package com.crowd.mvc.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/*
* 配置文件映射类
* */
@PropertySource(value = {"classpath:jdbc.properties","classpath:configuration.properties"})
@Configuration("propertiesConfig")
public class PropertiesConfig {

    @Value("${jdbc.userId}")
    private String username;
    @Value("${jdbc.pwd}")
    private String password;
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${mybatis.mybatisTypeAliasPackage}")

    private String mybatisTypeAliasPackage;
    @Value("${mybatis.mapperLocations}")
    private String mapperLocations;

    @Value("${jsp.prefix}")
    private String prefix;

    @Value("${jsp.suffix}")
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    String getMybatisTypeAliasPackage() {
        return mybatisTypeAliasPackage;
    }

    public void setMybatisTypeAliasPackage(String mybatisTypeAliasPackage) {
        this.mybatisTypeAliasPackage = mybatisTypeAliasPackage;
    }

    String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
