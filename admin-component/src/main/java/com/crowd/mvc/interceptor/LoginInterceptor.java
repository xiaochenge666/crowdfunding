package com.crowd.mvc.interceptor;

import com.crowd.constant.CrowdConstant;
import com.crowd.entity.Admin;
import com.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
* 拦截指定请求，检测他们是否登录，通过获取session对象中是否存在currentAdmin对象，来判断用户是否登录
* */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response , Object handler) throws Exception {

        //从session中获取当前登录对象
        HttpSession httpSession=request.getSession();
        Admin user = (Admin) httpSession.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        if(user==null){//如果为空说明未登录
            //抛出无权限访问异常
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_NOACCESSPERMISION);
        }
        return true;
    }
}
