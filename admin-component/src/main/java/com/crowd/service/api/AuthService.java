package com.crowd.service.api;

import com.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Auth> queryAuthByRoleId(Integer roleId);

    void reassignAuthByRoleId(Map<String, Object> par);
}
