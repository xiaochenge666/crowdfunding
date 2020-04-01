package com.crowd.dao;

import com.crowd.entity.Admin;

import java.util.List;

public interface AdminMapper {
    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> queryAll();

    Admin findUserByName(String loginacount);

    List<Admin> selectAdminListByKeyWord(String keyword);

    int deleteUser(int id);
}