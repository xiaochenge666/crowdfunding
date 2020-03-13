package com.crowd.service.Impl;

import com.crowd.dao.adminMapper;
import com.crowd.entity.admin;
import com.crowd.service.api.adminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class adminServiceImpl implements adminService {
    @Resource
    adminMapper adminMapper;

    public void saveAdmin(admin admin) {
        admin = new admin(null,"111","111","222","223",new Date());
        adminMapper.insert(admin);
        throw new RuntimeException();

    }

    public void queryAll() {
        System.out.println(adminMapper.queryAll());
    }
}
