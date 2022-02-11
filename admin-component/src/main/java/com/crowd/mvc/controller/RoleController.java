package com.crowd.mvc.controller;

import com.crowd.entity.Role;
import com.crowd.service.api.RoleService;
import com.crowd.utils.ResponseEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    //分页查询角色
    @RequestMapping("/query")
    @ResponseBody
    public ResponseEntity<PageInfo<Role>> queryRoleByPage(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                                          @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                                          @RequestParam(value = "keyword",defaultValue = "") String keyword){
        PageInfo<Role> rolePageInfo = roleService.selectByKeyword(pageNo, pageSize, keyword);
        return ResponseEntity.successWithData(rolePageInfo);
    }

    //添加用户
    @RequestMapping("/add")
    @ResponseBody
    public ResponseEntity addRole(Role role){
        roleService.addRole(role);
        return ResponseEntity.successWithoutData();
    }

    //添加用户
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseEntity editRole(Role role){
        roleService.editRole(role);
        return ResponseEntity.successWithoutData();
    }
}
