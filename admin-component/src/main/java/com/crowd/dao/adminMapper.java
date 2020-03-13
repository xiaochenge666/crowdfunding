package com.crowd.dao;

import com.crowd.entity.admin;

import java.util.List;

public interface adminMapper {
    int insert(admin record);

    int insertSelective(admin record);

    List<admin> queryAll();
}