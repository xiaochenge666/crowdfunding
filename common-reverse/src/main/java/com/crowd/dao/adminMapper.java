package com.crowd.dao;

import com.crowd.entity.admin;

public interface adminMapper {
    int insert(admin record);

    int insertSelective(admin record);
}