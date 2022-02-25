package com.crowd.dao;
import com.crowd.entity.Admin;
import com.crowd.entity.Role;
import com.crowd.mvc.config.SpringConfig;
import com.crowd.mvc.config.SpringMVCConfig;
import com.crowd.service.api.AdminService;
import com.crowd.service.api.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(classes = {SpringConfig.class})
@SpringJUnitWebConfig(classes = {SpringMVCConfig.class})

public class test {

    @Autowired
    DataSource dataSource;

    @Test
    public void test() throws SQLException {
        System.out.print(dataSource.getConnection());
    }

    @Autowired
    AdminMapper adminMapper;

    @Test
    public void test1(){
        System.out.println(adminMapper.queryAll());
    }
    @Autowired
    AdminService adminservice;

    @Autowired
    RoleMapper roleMapper;

    @Test
    public void test10(){

        for (int i = 0; i <20 ; i++) {
            Role role = new Role();
            role.setName("cc"+i);
            roleMapper.insert(role);
        }

    }

    @Test
    public void test2(){
        adminservice.saveAdmin(new Admin());
    }

    @Test
    public void test3(){
        Logger logger = LoggerFactory.getLogger(test.class);
        Admin cc = adminMapper.findUserByName("cc");
        System.out.println(cc);
        logger.info("你好");
    }

    @Test
    public void test4(){


        System.out.println(adminservice.getAdminByLoginAcct("199", "111").getUserPswd());
    }

    @Test

    public void test111(){
        List<Integer> roles = new ArrayList<>();
        roles.add(1);
        roles.add(17);
        roles.add(20);
        Map<String,Object> map = new HashMap<>();
        map.put("id",207);
        map.put("newRolesAssign",roles);
        adminMapper.addNewAssignRoleByUserId(207,roles);
    }



}
