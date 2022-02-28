package com.crowd.mvc.config.auth;

import com.crowd.mvc.annotaion.RootIgnore;
import com.crowd.service.api.AdminService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import javax.annotation.Resource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启注解支持
@RootIgnore//让root容器取消扫描
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Resource
    AdminService adminService;

    @Resource
    CrowdPasswordEncoder crowdPasswordEncoder;

    

    //配置用户有什么权限
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminService).passwordEncoder(crowdPasswordEncoder);
    }


    //配置相关请求时需要什么权限（授权）
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()//无需认证直接放行
                .antMatchers("/admin/doLogin").permitAll()
                .antMatchers("/admin/toLogin").permitAll()
                .antMatchers("/admin/doLoginAfter").permitAll()
                .antMatchers("/admin/login/fail").permitAll()
                .antMatchers("/bootstrap/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/jquery/**").permitAll()
                .antMatchers("/layer/**").permitAll()
                .antMatchers("/script/**").permitAll()
                .antMatchers("/ztree/**").permitAll()
            .and()
            .authorizeRequests()//需要认证才可访问
                .anyRequest().authenticated()
            .and()
            .formLogin()//开启表单登录
                .loginPage("/admin/toLogin")
                .loginProcessingUrl("/admin/doLogin")
                .usernameParameter("username")
                .passwordParameter("password")
                .successForwardUrl("/admin/doLoginAfter")
                .failureForwardUrl("/admin/login/fail")
        ;
    }


}
