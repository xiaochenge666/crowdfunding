package com.crowd.mvc.config;

import com.crowd.exception.AccessForbiddenException;
import com.crowd.exception.LoginFailedException;
import com.crowd.utils.CrowdUtils;
import com.crowd.utils.ResponseEntity;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CrowdExceptionResolver {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    private ModelAndView resolveCommonException(Exception e,
                                                HttpServletRequest request,
                                                HttpServletResponse response,
                                                String viewName) throws IOException {
        /*
        * 这是一个工具方法
        * */

        //首先判断请求类型ajax/html
        boolean isAjax= CrowdUtils.isAjaxRequest(request);
        String exceptionInfo=e.getMessage();//异常信息
        e.printStackTrace();
        logger.error("error:"+exceptionInfo+"\t e.toString():"+e.toString());

        //若是ajax请求
        if(isAjax){
            ResponseEntity<Object> responseEntity= ResponseEntity.fail(exceptionInfo+"出错");
            Gson gson=new Gson();
            String toJson = gson.toJson(responseEntity);
            System.out.println(toJson);
            PrintWriter writer=response.getWriter();
            writer.write(toJson);
            return null;
        }
        //否则
        ModelAndView modelAndView=new ModelAndView();
        String ATTRIBUTE_NAME = "exceptionInfo";
        modelAndView.addObject(ATTRIBUTE_NAME,exceptionInfo);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }


    //下边为各种异常的处理方法
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView handelNullPointException(Exception e,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response
                                                 ) throws IOException {
        String viewName="error";
        return this.resolveCommonException(e,request,response,viewName);
    }

    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView loginFailed(Exception e,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        String viewName="admin-login";
        return this.resolveCommonException(e,request,response,viewName);
    }

    @ExceptionHandler(value = AccessForbiddenException.class)
    public ModelAndView handelAccessForbiddenException(Exception e,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        String viewName="admin-login";
        return this.resolveCommonException(e,request,response,viewName);
    }


}
