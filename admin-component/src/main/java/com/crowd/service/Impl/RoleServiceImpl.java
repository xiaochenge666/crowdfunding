package com.crowd.service.Impl;

import com.crowd.dao.RoleMapper;
import com.crowd.entity.Role;
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
        try {
            roleMapper.updateRole(role);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException("更新失败！"+e.getMessage());
        }

    }
}
