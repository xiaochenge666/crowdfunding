package com.crowd.service.api;

import com.crowd.dao.adminMapper;
import com.crowd.entity.admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface adminService {

    void saveAdmin(admin admin);
    void queryAll();
}
