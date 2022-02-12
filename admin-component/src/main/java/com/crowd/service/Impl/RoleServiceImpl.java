package com.crowd.service.Impl;

import com.crowd.dao.RoleMapper;
import com.crowd.entity.Role;
import com.crowd.exception.ParameterIllegalException;
import com.crowd.exception.RecordHasExistedException;
import com.crowd.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;


@Service
public class RoleServiceImpl implements RoleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RoleMapper roleMapper;

    @Override
    public PageInfo<Role> selectByKeyword(int pageNo, int pageSize, String keyword) {

        PageHelper.startPage(pageNo,pageSize);

        List<Role> roles = roleMapper.selectByKeyword(keyword);

        return new PageInfo<Role>(roles);
    }

    @Override
    public void addRole(Role role) {
        try {
            roleMapper.insert(role);
        }catch (Exception e){
            logger.error("添加角色失败，原因："+e.getMessage());
            if (e instanceof DuplicateKeyException){
                throw new RecordHasExistedException();
            }else {
                throw new RuntimeException("添加失败！"+e.getMessage());
            }
        }

    }

    @Override
    public void editRole(Role role) {
        if (role==null||role.getName()==null||"".equals(role.getName().trim())){
            logger.error("参数不能为空，不合法！");
            throw new ParameterIllegalException();
        }

        try {
            roleMapper.updateRole(role);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException("更新失败！"+e.getMessage());
        }
    }

    @Override
    public void removeRole(Role role) {
        try {
            roleMapper.deleteRole(role);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException("删除角色失败！");
        }
    }

    @Override
    public void removeRoleBatch(List<Role> roles) {
        if (roles==null||roles.size()==0){
            logger.error("参数为空！");
            throw new ParameterIllegalException();
        }
        try {
            Stream<Role> stream = roles.stream();
            stream.forEach(x->{
               roleMapper.deleteRole(x);
            });
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }


    }
}
