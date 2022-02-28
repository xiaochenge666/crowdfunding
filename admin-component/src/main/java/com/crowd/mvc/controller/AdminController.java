package com.crowd.mvc.controller;
import com.crowd.constant.CrowdConstant;
import com.crowd.entity.Admin;
import com.crowd.exception.AccessForbiddenException;
import com.crowd.mvc.config.auth.CrowdPasswordEncoder;
import com.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @RequestMapping("/doLoginAfter")
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

    //分页显示/查询用户列表
    @RequestMapping("/do/page")
    public String getPageInfo(
            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            @RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5") int pageSize, Model model
            ){
        PageInfo<Admin> pageInfo = adminservice.getPageInfo(keyword, pageNo, pageSize);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("keyword",keyword);
        return "admin-user";
    }


    @RequestMapping("/do/remove")
    @PreAuthorize("hasAuthority('user:delete')")
    public String remove(@RequestParam(value = "id") int id,
                              @RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                              @RequestParam(value = "keyword",defaultValue = "") String keyword) {
        adminservice.deleteAdmin(id);
        return "redirect:/admin/do/page?pageNo="+pageNo+"&keyword="+keyword;
    }

    @PreAuthorize(value = "hasAuthority('user:add')")
    @RequestMapping("do/add")
    public String addAdmin(Admin admin){
        adminservice.addAdmin(admin);
        return "redirect:/admin/do/page?pageNo="+Integer.MAX_VALUE;
    }

    @PreAuthorize(value = "hasAuthority('user:edit')")
    @RequestMapping("go/edit")
    public String goEditAdminPage(@RequestParam("id") int id,
                                  @RequestParam("pageNo") int pageNo,
                                  @RequestParam("keyword") String keyword,
                                  Model model){
        Admin admin = adminservice.queryAdmin(id);
        model.addAttribute("user",admin);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("keyword",keyword);
        return "admin-user-edit";
    }

    //编辑
    @PreAuthorize(value = "hasAuthority('user:edit')")
    @RequestMapping("do/edit")
    public String editAdmin(Admin admin,@RequestParam("pageNo") Integer pageNo,
                              @RequestParam("keyword") String keyword){
        adminservice.updateAdminSelective(admin);
        return "redirect:/admin/do/page?pageNo="+pageNo+"&keyword"+keyword;
    }

    //登录失败！
    @RequestMapping("login/fail")
    public void AuthFail(){
        throw new AccessForbiddenException("账号或密码错误！");
    }

}
