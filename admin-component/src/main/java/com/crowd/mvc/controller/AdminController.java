package com.crowd.mvc.controller;
import com.crowd.constant.CrowdConstant;
import com.crowd.entity.Admin;
import com.crowd.service.api.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
@Controller
public class AdminController {
    @Resource
    AdminService adminservice;

    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("username") String name,
                          @RequestParam("password") String pwd,
                          HttpSession session){
        // 若返回admin则登陆成功，否则失败
        Admin admin = adminservice.getAdminByLoginAcct(name,pwd);

        // 登陆成功返回的admin对象存入Session域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);

        return "";
    }

    @RequestMapping("/admin/test.html")
    public String doTest(){
        System.out.println(adminservice.queryAll());
        return null;
    }








}
