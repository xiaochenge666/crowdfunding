package com.crowd.mvc.interceptor;

import com.crowd.constant.CrowdConstant;
import com.crowd.entity.Admin;
import com.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response , Object handler){

        //从session中获取当前登录对象
        HttpSession httpSession=request.getSession();
        Admin user = (Admin) httpSession.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        if(user==null){//如果为空说明未登录
            //抛出无权限访问异常
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_NO_ACCESS_PERMISSION);
        }
        return true;
    }
}
