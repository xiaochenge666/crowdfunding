package com.crowd.mvc.controller;

import com.crowd.entity.Auth;
import com.crowd.entity.Role;
import com.crowd.service.api.AdminService;
import com.crowd.service.api.AuthService;
import com.crowd.utils.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/assign")
public class AssignController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthService authService;

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

    @RequestMapping("/auth/query/by/role/id")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> queryAuthByRole(@RequestParam("role_id") Integer roleId){
        List<Auth> authList = authService.queryAuthByRoleId(roleId);
        Map<String,Object> responseData = new HashMap<>();
        responseData.put("roleId",roleId);
        responseData.put("authList",authList);
        return ResponseEntity.successWithData(responseData);
    }

    @RequestMapping ("/auth/update/by/role/id")
    @ResponseBody
    public ResponseEntity reassignAuthByRoleId(@RequestBody Map<String,Object> par){
        /**
         * par:       1         2
         *   key:   role_id    Integer   角色id
         *   value: auth_list   List<Integer>  权限ID的集合
         * */
        authService.reassignAuthByRoleId(par);
        return ResponseEntity.successWithoutData();
    }

}
