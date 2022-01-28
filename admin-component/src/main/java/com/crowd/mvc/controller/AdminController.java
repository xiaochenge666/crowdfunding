package com.crowd.mvc.controller;
import com.crowd.constant.CrowdConstant;
import com.crowd.entity.Admin;
import com.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

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
        return "redirect:/admin/toMain";
    }

    @GetMapping("/logout")
    public String goLogin(HttpSession session){
        session.invalidate();//让session失效
        return "redirect:/admin/toLogin";
    }

    @PostMapping("/do/page")
    public String getPageInfo(
            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            @RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5") int pageSize, Model model
            ){
        PageInfo<Admin> pageInfo = adminservice.getPageInfo(keyword, pageNo, pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "admin-sidebar-user";
    }

    @PostMapping("/do/remove/{id}/{pageNo}/{keyword}.html")
    public String remove(@PathVariable("id") int id,
                              @PathVariable("pageNo") int pageNo,
                              @PathVariable("keyword") String keyword) {
        adminservice.deleteAdmin(id);
        return "redirect:/admin/do/page.html?pageNo="+pageNo+"&keyword="+keyword;
    }

    @RequestMapping("do/add")
    public String addAdmin(Admin admin){
        adminservice.addAdmin(admin);
        return "redirect:/admin/do/page.html";
    }

    @RequestMapping("go/edit")
    public String goEditAdminPage(String  logincount,Model model){
        Admin admin = adminservice.queryAdmin(logincount);
        model.addAttribute("currAdmin",admin);
        return null;
    }

    @RequestMapping("do/edit")
    public String editAdmin(Admin admin){
        adminservice.updateAdmin(admin);
        return null;
    }




}
