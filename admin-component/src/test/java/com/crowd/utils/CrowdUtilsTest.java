package com.crowd.utils;
import com.crowd.entity.Admin;
import com.crowd.mvc.config.SpringConfig;
import com.crowd.mvc.config.SpringMVCConfig;
import com.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(classes = {SpringConfig.class})
@SpringJUnitWebConfig(classes = {SpringMVCConfig.class})
public class CrowdUtilsTest {
    @Resource
    AdminService adminService;



    @Test
    public  void test(){
        String pwd = "123Abcde23213";
        pwd = CrowdUtils.md5(pwd);
        System.out.println(pwd);
    }

    @Test
    public void test1(){
        Admin admin = new Admin();
        admin.setId(303);
        admin.setUserName("陈城");
        admin.setLoginAcct("1999");
        admin.setEmail("1430986978@qq.com");
        admin.setUserPswd("111");
        adminService.addAdmin(admin);
    }

}
