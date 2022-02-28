package com.crowd.dao;

import com.crowd.entity.Auth;
import com.crowd.entity.AuthExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthMapper {
    long countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    List<Auth> selectHasAssignByRoleId(Integer roleId);

    void authEmptyByRoleId(Integer roleId);

    void setAuthByRoleId(@Param("roleId") Integer roleId,@Param("authList") List<Integer> authList);
}