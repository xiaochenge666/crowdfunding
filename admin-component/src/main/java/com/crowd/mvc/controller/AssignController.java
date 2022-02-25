package com.crowd.mvc.controller;

import com.crowd.entity.Role;
import com.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/assign")
public class AssignController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/to/assign/role")
    public String toAssignPage(@RequestParam("id") Integer id,
                               @RequestParam("pageNo") Integer pageNo,
                               @RequestParam("keyword") String keyword,
                               Model model){

        List<Role> rolesAssign = adminService.queryAlreadyAssignRole(id);
        List<Role> rolesUnAssign = adminService.queryUnAssignRole(id);

        model.addAttribute("rolesAssign",rolesAssign);
        model.addAttribute("rolesUnAssign",rolesUnAssign);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("keyword",keyword);
        model.addAttribute("id",id);

        return "admin-assign-role";
    }


    @RequestMapping("/role/submit")
    public String saveNewAssignRole(@RequestParam("id") Integer id,
                                    @RequestParam(value = "newRolesAssign",required = false) List<Integer> newRolesAssign,
                                    @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "keyword",defaultValue = "") String keyword){

        adminService.saveNewAssignRole(id,newRolesAssign);

        return "redirect:/admin/do/page?pageNo="+pageNo+"&keyword="+keyword;
    }

}
