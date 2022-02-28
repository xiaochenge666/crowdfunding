package com.crowd.service.Impl;

import com.crowd.dao.AuthMapper;
import com.crowd.entity.Auth;
import com.crowd.entity.AuthExample;
import com.crowd.exception.ParameterIllegalException;
import com.crowd.service.api.AuthService;
import com.crowd.utils.CrowdUtils;
import org.aspectj.lang.annotation.DeclareWarning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> queryAuthByRoleId(Integer roleId) {
        //查出所有的权限
        List<Auth> authList = authMapper.selectByExample(new AuthExample());

        //查出当前角色具备的权限
        List<Auth> hasAssignAuth = authMapper.selectHasAssignByRoleId(roleId);

        //给已经分配的权限设置已分配标识
        authList.forEach(e->{
            if (hasAssignAuth.contains(e)){
                e.setAssign(true);
            }
        });

        return authList;
    }

    @Override
    public void reassignAuthByRoleId(Map<String, Object> par) {
        List<Integer> authList;//当前角色具有的权限列表
        Integer roleId;//角色id

        try {
            Object obj = par.get("auth_list");
            roleId = Integer.parseInt((String) par.get("role_id"));
            authList = CrowdUtils.castList(obj, Integer.class);

        }catch (Exception e){
            logger.error("参数解析时出错！"+e.getMessage());
            e.printStackTrace();
            throw new ParameterIllegalException("参数不合法！");
        }

        //先删除已有的角色权限关系
        authMapper.authEmptyByRoleId(roleId);
        if (authList==null||authList.size()==0){
            logger.warn("角色id："+roleId+"的权限被清空了！");
            return;
        }

        //重新添加角色权限关系
        authMapper.setAuthByRoleId(roleId,authList);


    }
}
