package com.crowd.service.api;
import com.crowd.entity.Admin;
import com.crowd.entity.Role;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface AdminService extends UserDetailsService {

    List<Admin> queryAll();

    Admin getAdminByLoginAcct(String name, String pwd);

    PageInfo<Admin> getPageInfo(String keyword,int pageNo,int pageSize);

    void deleteAdmin(int id);

    void addAdmin(Admin admin);

    void updateAdmin(Admin admin);

    Admin queryAdmin(int id);

    void updateAdminSelective(Admin admin);

    List<Role> queryAlreadyAssignRole(Integer id);

    List<Role> queryUnAssignRole(Integer id);

    void saveNewAssignRole(Integer id, List<Integer> newRolesAssign);
}
