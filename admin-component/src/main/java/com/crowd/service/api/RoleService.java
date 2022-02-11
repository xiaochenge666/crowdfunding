package com.crowd.service.api;

import com.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

public interface RoleService {
    PageInfo<Role> selectByKeyword(int pageNo,int pageSize,String keyword);
    void addRole(Role role);

    void editRole(Role role);
}
