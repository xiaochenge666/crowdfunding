package com.crowd.mvc.config;

import com.crowd.constant.CrowdConstant;
import com.crowd.exception.*;
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


/*
* 异常处理器
* */
@ControllerAdvice
public class CrowdExceptionResolver {
    private Logger logger= LoggerFactory.getLogger(this.getClass());


     //这是一个工具方法,用于响应不同类型的请求（按照不同的响应类型进行响应）
     private ModelAndView resolveCommonException(Exception e,
                                                HttpServletRequest request,
                                                HttpServletResponse response,
                                                String viewName) throws IOException {


        //首先判断请求类型ajax/html
        boolean isAjax= CrowdUtils.isAjaxRequest(request);
        String exceptionInfo=e.getMessage();//异常信息

        e.printStackTrace();//打印具体报错行，便于调试跟踪！
        //日志输出错误信息
        logger.error("error:"+exceptionInfo+"\t e.toString():"+e.toString());

        //若是ajax请求，则响应形式则按照json形式进行响应！
        if(isAjax){
            ResponseEntity<Object> responseEntity= ResponseEntity.fail(exceptionInfo);
            Gson gson=new Gson();
            String toJson = gson.toJson(responseEntity);
//            System.out.println(toJson);
            logger.info("一个异常信息被转换成json字符串，并被转发给了用户！："+toJson);
            PrintWriter writer=response.getWriter();
            writer.write(toJson);
            writer.close();
            return null;
        }

        //否则，通过modelAndView转发到指定的错误提示页面
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject(CrowdConstant.ATTRIBUTE_NAME,exceptionInfo);//错误信息
        modelAndView.setViewName(viewName);//指定的视图名
        return modelAndView;
    }


    //下边为各种异常的处理方法
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView loginFailed(Exception e,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        return this.resolveCommonException(e,request,response,CrowdConstant.ADMIN_LOGIN_VIEW);//异常后去登录页面
    }

    //没有权限
    @ExceptionHandler(value = AccessForbiddenException.class)
    public ModelAndView handelAccessForbiddenException(Exception e,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        return this.resolveCommonException(e,request,response,CrowdConstant.ADMIN_LOGIN_VIEW);
    }

    //用户已经存在时
    @ExceptionHandler(value = UserHasExistedException.class)
    public ModelAndView handelUserExistException(Exception e,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) throws IOException {
        return this.resolveCommonException(e,request,response,CrowdConstant.ADMIN_ADD_VIEW);
    }

    //处理用户编辑时的异常
    @ExceptionHandler(value = UserNotExistException.class)
    public ModelAndView handelEditException(Exception e,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) throws IOException {
        return this.resolveCommonException(e,request,response,CrowdConstant.ADMIN_EDIT_VIEW);
    }

    //统一服务器错误页面
    @ExceptionHandler(value = {AddAdminException.class,RuntimeException.class,NullPointerException.class})
    public ModelAndView handel500Error(Exception e,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
        return this.resolveCommonException(e,request,response,CrowdConstant.ERROR_VIEW);
    }


}
