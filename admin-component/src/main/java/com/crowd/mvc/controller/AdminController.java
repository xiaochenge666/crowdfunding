package com.crowd.mvc.controller;
import com.crowd.constant.CrowdConstant;
import com.crowd.entity.Admin;
import com.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class AdminController {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Resource
    AdminService adminservice;

    @RequestMapping("/doLogin")
    public String doLogin(@RequestParam("username") String name,
                          @RequestParam("password") String pwd,
                          HttpSession session){
        logger.info("接到的参数："+name+","+pwd);

        // 若返回admin则登陆成功，否则失败
        Admin admin = adminservice.getAdminByLoginAcct(name,pwd);
        logger.info("验证通过！");
        // 登陆成功返回的admin对象存入Session域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);
        return "redirect:/admin/toMain.html";
    }

    @RequestMapping("/logout")
    public String goLogin(HttpSession session){
        session.invalidate();
        return "redirect:/admin/toLogin.html";
    }

    @RequestMapping("/do/page")
    public String getPageInfo(
            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            @RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5") int pageSize, Model model
            ){
        PageInfo<Admin> pageInfo = adminservice.getPageInfo(keyword, pageNo, pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "admin-sidebar-user";
    }

    @RequestMapping("/do/remove/{id}/{pageNo}/{keyword}.html")
    public String remove(@PathVariable("id") int id,
                              @PathVariable("pageNo") int pageNo,
                              @PathVariable("keyword") String keyword) {
        adminservice.deleteAdmin(id);
        return "redirect:/admin/do/page.html?pageNo="+pageNo+"&keyword="+keyword;
    }



}
