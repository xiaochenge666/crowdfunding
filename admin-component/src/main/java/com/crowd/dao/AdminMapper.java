package com.crowd.dao;

import com.crowd.entity.Admin;
import com.crowd.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface AdminMapper {
    int insert(Admin record);

    int insertSelective(Admin record);

    void updateSelective(Admin admin);

    List<Admin> queryAll();

    Admin findUserByName(String loginacount);

    List<Admin> selectAdminListByKeyWord(String keyword);

    int deleteUser(int id);


    void updateAdmin(Admin admin);

    Admin findUserById(int id);

    List<Role> queryAlreadyAssignRole(Integer id);

    List<Role> queryUnAssignRole(Integer id);

    void removeAllAssignRoleByUserId(Integer id);


    void addNewAssignRoleByUserId(@Param("id") Integer id, @Param("newRolesAssign") List<Integer> newRolesAssign);
}