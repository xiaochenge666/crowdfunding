package com.crowd.service.Impl;

import com.crowd.dao.AdminMapper;
import com.crowd.entity.Admin;
import com.crowd.exception.LoginFailedException;
import com.crowd.service.api.AdminService;
import com.crowd.utils.CrowdUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminMapper adminMapper;

    public void saveAdmin(Admin admin) {
        if (admin!=null&&admin.getUserPswd()!=null){
            //保存用户时需要对密码进行md5加密处理
            String userPswd = admin.getUserPswd();
            admin.setUserPswd(CrowdUtils.md5(userPswd));
        }
        String pwd=CrowdUtils.md5("111");
        admin = new Admin(null,"1999",pwd,"222","223",new Date());



        int code = adminMapper.insert(admin);
    }

    public List<Admin> queryAll() {
        return adminMapper.queryAll();
    }

    public Admin getAdminByLoginAcct(String loginacount, String pwd) {
        // 1.查用户
        Admin admin=adminMapper.findUserByName(loginacount);
        // 2.判断有无，无抛异常
        if(admin==null){
            throw new LoginFailedException("该用户不存在");
        }
        // 3.取出密码
        String pwdDatabase = admin.getUserPswd();

        //4.表单提交过来的密码进行加密
        String pwdForm = CrowdUtils.md5(pwd);
        //5.两个密码进行对比
        if(pwdDatabase.equals(pwdForm)){
            return admin;
        }
        //6.不一样抛异常，一样返回admin
        throw new LoginFailedException();
    }

}
